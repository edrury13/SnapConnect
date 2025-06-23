# SnapConnect: Product Requirements Document (PRD)

## 1. Introduction

SnapConnect is a mobile social media application designed for users to share moments with friends through images and videos in real-time. The core concept revolves around ephemeral content, where shared media is automatically deleted after 24 hours, fostering spontaneous and authentic communication. The app will also feature robust social functionalities including a friend system, commenting, stories, and group messaging.

## 2. Project Goals and Objectives

*   **Primary Goal:** To create an engaging and intuitive Android application that allows users to connect and share their lives with friends through ephemeral media.
*   **Objectives:**
    *   Develop a secure and scalable user authentication system.
    *   Implement real-time features for content sharing and messaging.
    *   Ensure a seamless and responsive user experience.
    *   Build a reliable system for managing friendships and social interactions.
    *   Leverage Supabase for all backend services, including authentication, database, storage, and serverless functions.

## 3. Target Audience

*   **Primary:** Teenagers and young adults (ages 16-25) who are proficient with social media and value instant, visual, and private communication with their close friends.
*   **Secondary:** A broader audience looking for a fun and simple way to share day-to-day moments with family and a select group of friends without the pressure of a permanent digital footprint.

## 4. Core Features

### 4.1. User Authentication
*   **Sign Up:** Users can create a new account using an email and password.
*   **Log In:** Registered users can log in to their accounts.
*   **Log Out:** Users can securely log out of their accounts.
*   **User Sessions:** User sessions will be managed securely to keep them logged in across app launches.

### 4.2. Friends Management
*   **Add Friends:** Users can search for other users by their unique username and send friend requests.
*   **Friend Requests:** Users will receive notifications for new friend requests and can accept or decline them.
*   **Friends List:** Users can view a list of their current friends.
*   **Delete Friends:** Users can remove other users from their friends list.

### 4.3. Real-time Image and Video Sharing
*   **Camera Integration:** Users can access the device camera from within the app to capture photos and record videos.
*   **Content Posting:** Users can post captured images and videos.
*   **Audience Selection:** Before posting, users can choose to share with specific friends, add to their story, or send to a group chat.

### 4.4. Ephemeral Content (Stories)
*   **24-Hour Lifetime:** Images and videos posted to a user's "Story" will be visible to their friends for 24 hours.
*   **Automatic Deletion:** After 24 hours, the content will be automatically and permanently deleted from the backend. This will be handled by a Supabase Edge Function or a database trigger.
*   **Story Viewing:** Users can tap on a friend's profile/name to view their current story.

### 4.5. Comments
*   **Add Comments:** Users can write and post text-based comments on the images and videos within their friends' stories.
*   **View Comments:** Comments on a story post will be visible to the post's author and other friends who can view the story.

### 4.6. Group Messaging
*   **Group Creation:** Users can create group chats and add multiple friends to the conversation.
*   **Text Messaging:** Members of a group can send and receive text messages in real-time.
*   **Media in Chat:** Users can share images and videos (snaps) directly within the group chat.

## 5. Technical Stack

*   **Frontend:** Android (Kotlin)
    *   **UI:** Jetpack Compose for building a modern, declarative UI.
    *   **Architecture:** MVVM (Model-View-ViewModel)
*   **Backend:** Supabase
    *   **Authentication:** Supabase Auth for user sign-up and log-in.
    *   **Database:** Supabase (Postgres) for storing user data, friend relationships, comments, and message history.
    *   **Storage:** Supabase Storage for hosting user-uploaded images and videos.
    *   **Realtime:** Supabase Realtime for instant messaging and live updates.
    *   **Edge Functions:** Serverless functions for custom logic, such as the automatic deletion of posts after 24 hours.

## 6. Success Metrics

*   **User Engagement:** Daily Active Users (DAU) and Monthly Active Users (MAU).
*   **Content Creation:** Number of images and videos posted per day.
*   **Social Interaction:** Number of friend requests sent, comments made, and group messages exchanged.
*   **User Retention:** Percentage of users returning to the app week-over-week and month-over-month.
*   **Performance:** App load time, crash rate, and API response times.

## 7. Future Considerations (Roadmap V2)

*   **Direct Messaging (1-on-1):** Private, ephemeral messaging between two users.
*   **AR Filters & Lenses:** Fun, interactive augmented reality filters for photos and videos.
*   **"Memories" Feature:** An opt-in feature to save favorite posts before they are deleted.
*   **Push Notifications:** Notifications for new messages, friend requests, and comments.
*   **Social Logins:** Allow users to sign up/log in using Google, Apple, or other social providers. 