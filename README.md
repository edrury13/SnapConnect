# SplatChat Android App

SplatChat (formerly SnapConnect) is a mobile social media application designed for users to share moments with friends through images and videos in real-time. The core concept revolves around ephemeral content, where shared media is automatically deleted after 24 hours.

## Features

### Core Features
- **User Authentication**: Sign up and login with email/password
- **Friends Management**: Add friends, manage friend requests, view friends list
- **Real-time Image and Video Sharing**: Share photos and videos with friends
- **Ephemeral Content (Stories)**: Posts automatically delete after 24 hours
- **Comments**: Comment on friends' stories with real-time updates
- **Group Messaging**: Create group chats and share content
- 📸 **Camera Integration** - Capture photos and videos with custom filters
- 📱 **Stories** - Share ephemeral content that disappears after 24 hours
- 💬 **Real-time Messaging** - Chat with friends using Supabase real-time subscriptions
- 🎭 **AR Filters** - Apply face filters using ML Kit face detection
- 🎨 **Material Design 3** - Modern UI with dynamic theming

### Advanced Features
- **Story Reactions**: Like and dislike stories with real-time counts
- **User Score System**: Profile displays total score (likes minus dislikes across all stories)
- **Story Auto-Advance**: Stories auto-advance after 8 seconds with toggle control
- **Interactive Story Viewer**: Progress bars, manual navigation, pause functionality
- **Tutorial System**: Guided onboarding for new users
- **Privacy Controls**: Granular permission management for camera, notifications, storage
- **AI-Powered Inspiration**: 
  - AI-generated captions and mood boards
  - Style analysis and tagging
  - Style-based story discovery
- **Media Preview**: Preview and edit content before posting
- **Profile Management**: 
  - Avatar upload and editing
  - Profile stats (stories, friends, score)
  - Display name and username customization
- **Style Gallery**: Browse and discover stories by artistic style
- **Notification Settings**: Customizable notification preferences
- **Story Privacy**: Set stories as public or friends-only
- **Enhanced Video Support**: Full video playback with auto-advance control

## Tech Stack

- **Frontend**: Android (Kotlin) with Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Backend**: Supabase (Auth, Database, Storage, Realtime)
- **Dependency Injection**: Hilt
- **Navigation**: Navigation Compose
- **Image Loading**: Coil
- **Camera**: CameraX

## Setup Instructions

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 17 or later
- Android SDK 34
- Minimum Android device/emulator with API 24 (Android 7.0)

### Supabase Setup

1. Create a Supabase project at [supabase.com](https://supabase.com)

2. Get your Supabase URL and Anon Key from the project settings

3. Run the database setup script in your Supabase SQL editor:
   - Execute the SQL in `supabase_setup.sql` to create tables and policies

4. Update the Supabase configuration in `app/src/main/java/com/example/snapconnect/di/AppModule.kt`:

```kotlin
// Replace with your Supabase project details
private const val SUPABASE_URL = "YOUR_SUPABASE_URL"
private const val SUPABASE_ANON_KEY = "YOUR_SUPABASE_ANON_KEY"
```

### Android App Setup

1. Clone the repository
2. Open the project in Android Studio
3. Sync the project and build
4. Run on device or emulator

## Project Structure

```
app/
├── src/main/java/com/example/snapconnect/
│   ├── data/
│   │   ├── model/          # Data models
│   │   └── repository/     # Repository classes
│   ├── di/                 # Dependency injection modules
│   ├── navigation/         # Navigation setup
│   ├── ui/
│   │   ├── screens/        # Composable screens
│   │   └── theme/          # App theming
│   ├── MainActivity.kt
│   └── SnapConnectApplication.kt
└── src/main/res/          # Resources
```

## Current Implementation Status

✅ **Completed:**
- Project setup with Jetpack Compose
- Dependency injection with Hilt
- Complete navigation structure with all screens
- Authentication system (Login/SignUp) with Supabase
- Main app structure with bottom navigation
- Data models for all features
- Theme and styling with SplatChat branding
- **Camera functionality** with filters and AR effects
- **Story system** with posting, viewing, and auto-advance
- **Real-time messaging** and group chats
- **Friends management** with requests and social features
- **Story reactions** (likes/dislikes) with real-time updates
- **User profiles** with stats, avatars, and editing
- **Privacy controls** and permission management
- **AI integration** for captions, style analysis, and inspiration
- **Tutorial system** for onboarding
- **Media storage** and retrieval via Supabase
- **Story privacy** controls (public/friends-only)
- **Notification system** and settings


## Development Notes

- The app uses Material 3 design system with SplatChat branding
- All screens follow MVVM architecture pattern with Hilt dependency injection
- Supabase handles authentication, database, storage, and real-time features
- Camera and microphone permissions required for full functionality
- Story auto-advance timing: 8 seconds for images, video length for videos
- AI features require OpenAI API key and Pinecone vector database
- Row Level Security (RLS) policies enforce privacy controls

## License

This project is for educational purposes.

## Backend Micro-Service (RAG)

A lightweight FastAPI service (`snapconnect-backend/`) now powers the
Inspiration & Reference Engine.

**Key endpoints (protected by `X-API-Key`)**

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/v1/inspiration/moodboard` | Generate mood board from a text prompt |
| POST | `/api/v1/inspiration/style-analysis` | Analyse artistic style from caption text |

See the backend README for setup, seed scripts, and deployment notes.

## Recent Updates

### Story Features
- Stories can be set as **Public** (viewable by anyone) or **Private** (friends only)
- **Story Reactions**: Users can like or dislike stories with real-time count updates
- **Auto-advance**: Stories advance automatically after 8 seconds (toggleable)
- **Interactive Viewer**: Progress bars, manual navigation (tap left/right), pause on hold
- **Enhanced Media Support**: Full image and video support with optimized playback
- Privacy controls enforced via Row Level Security (RLS) policies in Supabase

### Group Chat Support
- Create group chats with multiple friends
- New "Create Group" screen accessible from Messages tab
- Groups stored in `groups` table with `member_ids` array
- Direct messages and group chats share the same messaging infrastructure

### AI-Powered Style Detection
- Automatic style tagging for stories using OpenAI embeddings + Pinecone vector search
- Supported styles include: pop-art, watercolor, cyberpunk, minimalist, and more
- Pipeline: Story creation → AI caption generation → Style detection → Vector storage
- Style tags stored in `style_tags` array column
- AI-generated captions stored in `ai_caption` column

### Backend Infrastructure
- FastAPI backend deployed on Render
- Pinecone vector database (1024-dimensional, serverless)
- LangChain integration for RAG (Retrieval Augmented Generation)
- Style taxonomy namespace for style similarity search

## Backend Configuration

### Required Environment Variables
```bash
# OpenAI
OPENAI_API_KEY=your-openai-api-key

# Pinecone Vector Database
PINECONE_API_KEY=your-pinecone-api-key
PINECONE_ENVIRONMENT=us-east-1-aws  # Your index region
PINECONE_INDEX_NAME=snapconnect     # 1024-dim index

# Supabase
SUPABASE_URL=your-supabase-url
SUPABASE_SERVICE_ROLE_KEY=your-service-role-key
```

### User Experience Features
- **Tutorial System**: Interactive onboarding for new users
- **Privacy Screen**: Comprehensive permission management for camera, notifications, storage
- **Profile Management**: Avatar upload, profile editing, user statistics
- **User Score**: Dynamic score calculation (total likes - total dislikes)
- **Notification Settings**: Granular control over app notifications
- **Style Discovery**: Browse stories by AI-detected artistic styles

### Database Schema Updates
```sql
-- Stories table with full feature set
ALTER TABLE stories 
  ADD COLUMN is_public BOOLEAN DEFAULT true,
  ADD COLUMN style_tags TEXT[] DEFAULT '{}',
  ADD COLUMN ai_caption TEXT,
  ADD COLUMN likes_count INTEGER DEFAULT 0,
  ADD COLUMN dislikes_count INTEGER DEFAULT 0;

-- Story reactions table
CREATE TABLE story_reactions (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    story_id UUID REFERENCES stories(id) ON DELETE CASCADE,
    user_id UUID REFERENCES auth.users(id) ON DELETE CASCADE,
    reaction_type TEXT CHECK (reaction_type IN ('LIKE', 'DISLIKE')),
    created_at TIMESTAMPTZ DEFAULT NOW(),
    UNIQUE(story_id, user_id)
);

-- Row Level Security for private stories
CREATE POLICY select_stories_with_privacy
ON stories
FOR SELECT
USING (
    is_public
    OR user_id = auth.uid()
    OR EXISTS (
        SELECT 1
        FROM friends f
        WHERE f.status = 'ACCEPTED'
        AND (
            (f.user_id = auth.uid() AND f.friend_id = stories.user_id)
            OR (f.friend_id = auth.uid() AND f.user_id = stories.user_id)
        )
    )
);
``` 