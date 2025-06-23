-- SnapConnect Supabase Database Setup
-- Run these queries in order in your Supabase SQL editor

-- ========================================
-- 1. DATABASE TABLES
-- ========================================

-- Extended Users Table
CREATE TABLE public.users (
    id UUID REFERENCES auth.users(id) PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    username TEXT UNIQUE NOT NULL,
    display_name TEXT,
    avatar_url TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL
);

-- Create index for username searches
CREATE INDEX users_username_idx ON public.users(username);

-- Friends Table
CREATE TABLE public.friends (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID REFERENCES public.users(id) ON DELETE CASCADE,
    friend_id UUID REFERENCES public.users(id) ON DELETE CASCADE,
    status TEXT CHECK (status IN ('PENDING', 'ACCEPTED', 'BLOCKED')) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL,
    UNIQUE(user_id, friend_id)
);

-- Create indexes for friend queries
CREATE INDEX friends_user_id_idx ON public.friends(user_id);
CREATE INDEX friends_friend_id_idx ON public.friends(friend_id);
CREATE INDEX friends_status_idx ON public.friends(status);

-- Stories Table
CREATE TABLE public.stories (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID REFERENCES public.users(id) ON DELETE CASCADE,
    media_url TEXT NOT NULL,
    media_type TEXT CHECK (media_type IN ('IMAGE', 'VIDEO')) NOT NULL,
    caption TEXT,
    viewer_ids UUID[] DEFAULT ARRAY[]::UUID[],
    created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL,
    expires_at TIMESTAMPTZ DEFAULT NOW() + INTERVAL '24 hours' NOT NULL
);

-- Create indexes for story queries
CREATE INDEX stories_user_id_idx ON public.stories(user_id);
CREATE INDEX stories_expires_at_idx ON public.stories(expires_at);
CREATE INDEX stories_created_at_idx ON public.stories(created_at DESC);

-- Comments Table
CREATE TABLE public.comments (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    story_id UUID REFERENCES public.stories(id) ON DELETE CASCADE,
    user_id UUID REFERENCES public.users(id) ON DELETE CASCADE,
    text TEXT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL
);

-- Create indexes for comment queries
CREATE INDEX comments_story_id_idx ON public.comments(story_id);
CREATE INDEX comments_user_id_idx ON public.comments(user_id);

-- Groups Table
CREATE TABLE public.groups (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name TEXT NOT NULL,
    creator_id UUID REFERENCES public.users(id) ON DELETE CASCADE,
    member_ids UUID[] NOT NULL,
    avatar_url TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL
);

-- Create index for member queries
CREATE INDEX groups_member_ids_idx ON public.groups USING GIN(member_ids);

-- Messages Table
CREATE TABLE public.messages (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    group_id UUID REFERENCES public.groups(id) ON DELETE CASCADE,
    sender_id UUID REFERENCES public.users(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    media_url TEXT,
    media_type TEXT CHECK (media_type IN ('IMAGE', 'VIDEO')),
    created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL
);

-- Create indexes for message queries
CREATE INDEX messages_group_id_idx ON public.messages(group_id);
CREATE INDEX messages_created_at_idx ON public.messages(created_at DESC);

-- ========================================
-- 2. ENABLE ROW LEVEL SECURITY
-- ========================================

ALTER TABLE public.users ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.friends ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.stories ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.comments ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.groups ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.messages ENABLE ROW LEVEL SECURITY;

-- ========================================
-- 3. ROW LEVEL SECURITY POLICIES
-- ========================================

-- Users Table Policies
CREATE POLICY "Users can view all profiles" ON public.users
    FOR SELECT USING (true);

CREATE POLICY "Users can update own profile" ON public.users
    FOR UPDATE USING (auth.uid() = id);

CREATE POLICY "Users can insert own profile" ON public.users
    FOR INSERT WITH CHECK (auth.uid() = id);

-- Friends Table Policies
CREATE POLICY "Users can view own friendships" ON public.friends
    FOR SELECT USING (auth.uid() = user_id OR auth.uid() = friend_id);

CREATE POLICY "Users can send friend requests" ON public.friends
    FOR INSERT WITH CHECK (auth.uid() = user_id);

CREATE POLICY "Users can update friendships" ON public.friends
    FOR UPDATE USING (auth.uid() = user_id OR auth.uid() = friend_id);

CREATE POLICY "Users can delete friendships" ON public.friends
    FOR DELETE USING (auth.uid() = user_id OR auth.uid() = friend_id);

-- Stories Table Policies
CREATE POLICY "Users can view friends stories" ON public.stories
    FOR SELECT USING (
        user_id = auth.uid() OR 
        EXISTS (
            SELECT 1 FROM public.friends 
            WHERE status = 'ACCEPTED' 
            AND ((user_id = auth.uid() AND friend_id = stories.user_id) 
            OR (friend_id = auth.uid() AND user_id = stories.user_id))
        )
    );

CREATE POLICY "Users can create own stories" ON public.stories
    FOR INSERT WITH CHECK (auth.uid() = user_id);

CREATE POLICY "Users can update story views" ON public.stories
    FOR UPDATE USING (true);

CREATE POLICY "Users can delete own stories" ON public.stories
    FOR DELETE USING (auth.uid() = user_id);

-- Comments Table Policies
CREATE POLICY "Users can view comments" ON public.comments
    FOR SELECT USING (
        EXISTS (
            SELECT 1 FROM public.stories 
            WHERE stories.id = comments.story_id 
            AND (stories.user_id = auth.uid() OR EXISTS (
                SELECT 1 FROM public.friends 
                WHERE status = 'ACCEPTED' 
                AND ((user_id = auth.uid() AND friend_id = stories.user_id) 
                OR (friend_id = auth.uid() AND user_id = stories.user_id))
            ))
        )
    );

CREATE POLICY "Users can create comments" ON public.comments
    FOR INSERT WITH CHECK (auth.uid() = user_id);

CREATE POLICY "Users can delete own comments" ON public.comments
    FOR DELETE USING (auth.uid() = user_id);

-- Groups Table Policies
CREATE POLICY "Users can view own groups" ON public.groups
    FOR SELECT USING (auth.uid() = ANY(member_ids));

CREATE POLICY "Users can create groups" ON public.groups
    FOR INSERT WITH CHECK (auth.uid() = creator_id AND auth.uid() = ANY(member_ids));

CREATE POLICY "Creators can update groups" ON public.groups
    FOR UPDATE USING (auth.uid() = creator_id);

-- Messages Table Policies
CREATE POLICY "Users can view group messages" ON public.messages
    FOR SELECT USING (
        EXISTS (
            SELECT 1 FROM public.groups 
            WHERE groups.id = messages.group_id 
            AND auth.uid() = ANY(groups.member_ids)
        )
    );

CREATE POLICY "Users can send messages" ON public.messages
    FOR INSERT WITH CHECK (
        auth.uid() = sender_id AND EXISTS (
            SELECT 1 FROM public.groups 
            WHERE groups.id = group_id 
            AND auth.uid() = ANY(groups.member_ids)
        )
    );

-- ========================================
-- 4. STORAGE BUCKETS
-- ========================================

-- Create avatars bucket
INSERT INTO storage.buckets (id, name, public) VALUES ('avatars', 'avatars', true);

-- Create stories bucket
INSERT INTO storage.buckets (id, name, public) VALUES ('stories', 'stories', true);

-- Storage Policies for avatars bucket
CREATE POLICY "Users can upload own avatar" ON storage.objects
    FOR INSERT WITH CHECK (bucket_id = 'avatars' AND auth.uid()::text = (storage.foldername(name))[1]);

CREATE POLICY "Users can update own avatar" ON storage.objects
    FOR UPDATE USING (bucket_id = 'avatars' AND auth.uid()::text = (storage.foldername(name))[1]);

CREATE POLICY "Anyone can view avatars" ON storage.objects
    FOR SELECT USING (bucket_id = 'avatars');

-- Storage Policies for stories bucket
CREATE POLICY "Users can upload own stories" ON storage.objects
    FOR INSERT WITH CHECK (bucket_id = 'stories' AND auth.uid()::text = (storage.foldername(name))[1]);

CREATE POLICY "Anyone can view stories" ON storage.objects
    FOR SELECT USING (bucket_id = 'stories');

-- ========================================
-- 5. DATABASE FUNCTIONS & TRIGGERS
-- ========================================

-- Auto-create user profile on signup
CREATE OR REPLACE FUNCTION public.handle_new_user()
RETURNS trigger AS $$
BEGIN
    INSERT INTO public.users (id, email, username)
    VALUES (new.id, new.email, COALESCE(new.raw_user_meta_data->>'username', split_part(new.email, '@', 1)));
    RETURN new;
END;
$$ LANGUAGE plpgsql SECURITY DEFINER;

CREATE TRIGGER on_auth_user_created
    AFTER INSERT ON auth.users
    FOR EACH ROW EXECUTE FUNCTION public.handle_new_user();

-- Function to delete expired stories
CREATE OR REPLACE FUNCTION public.delete_expired_stories()
RETURNS void AS $$
BEGIN
    DELETE FROM public.stories WHERE expires_at < NOW();
END;
$$ LANGUAGE plpgsql;

-- ========================================
-- 6. CLEANUP TRIGGER WORKAROUND (Alternative to cron)
-- ========================================

-- Function to clean expired stories when accessing stories
CREATE OR REPLACE FUNCTION public.clean_expired_stories()
RETURNS trigger AS $$
BEGIN
    -- Delete expired stories (runs before the select)
    DELETE FROM public.stories WHERE expires_at < NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create a helper table to trigger the cleanup
CREATE TABLE IF NOT EXISTS public.story_cleanup_trigger (
    id SERIAL PRIMARY KEY,
    last_cleaned TIMESTAMPTZ DEFAULT NOW()
);

-- Insert a single row
INSERT INTO public.story_cleanup_trigger (id) VALUES (1) ON CONFLICT DO NOTHING;

-- Trigger that runs on update
CREATE TRIGGER clean_stories_trigger
    BEFORE UPDATE ON public.story_cleanup_trigger
    FOR EACH ROW EXECUTE FUNCTION public.clean_expired_stories();

-- Function to be called from your app periodically
CREATE OR REPLACE FUNCTION public.trigger_story_cleanup()
RETURNS void AS $$
BEGIN
    UPDATE public.story_cleanup_trigger SET last_cleaned = NOW() WHERE id = 1;
END;
$$ LANGUAGE plpgsql;

-- ========================================
-- 7. OPTIONAL: Enable pg_cron (if available)
-- ========================================

-- Uncomment these lines if pg_cron is available on your plan
-- CREATE EXTENSION IF NOT EXISTS pg_cron;
-- GRANT USAGE ON SCHEMA cron TO postgres;
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA cron TO postgres;
-- SELECT cron.schedule('delete-expired-stories', '*/5 * * * *', 'SELECT public.delete_expired_stories();');

-- ========================================
-- NOTES:
-- ========================================
-- 1. Run these queries in order
-- 2. Make sure to enable Email authentication in Supabase Dashboard
-- 3. Configure storage bucket permissions in Dashboard if needed
-- 4. Test all policies with different user scenarios
-- 5. For production, consider adding more indexes based on query patterns 