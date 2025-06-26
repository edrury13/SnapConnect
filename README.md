# SnapConnect Android App

SnapConnect is a mobile social media application designed for users to share moments with friends through images and videos in real-time. The core concept revolves around ephemeral content, where shared media is automatically deleted after 24 hours.

## Features

- **User Authentication**: Sign up and login with email/password
- **Friends Management**: Add friends, manage friend requests, view friends list
- **Real-time Image and Video Sharing**: Share photos and videos with friends
- **Ephemeral Content (Stories)**: Posts automatically delete after 24 hours
- **Comments**: Comment on friends' stories
- **Group Messaging**: Create group chats and share content

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

3. Update the Supabase configuration in `app/src/main/java/com/example/snapconnect/di/AppModule.kt`:

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
- Navigation structure
- Authentication screens (Login/SignUp)
- Main app structure with bottom navigation
- Data models for all features
- Theme and styling
- Basic screen placeholders

🚧 **To Be Implemented:**
- Supabase backend integration
- Camera functionality
- Real-time messaging
- Story posting and viewing
- Friends management
- Group chat functionality
- Media storage and retrieval

## Development Notes

- The app uses Material 3 design system
- All screens follow MVVM architecture pattern
- Supabase handles authentication, database, and real-time features
- Camera functionality will require device permissions

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