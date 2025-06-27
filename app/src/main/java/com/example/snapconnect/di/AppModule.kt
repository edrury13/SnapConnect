package com.example.snapconnect.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage
import javax.inject.Singleton
import com.example.snapconnect.services.NotificationService

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

import com.example.snapconnect.data.remote.InspirationApi
import com.example.snapconnect.data.repository.InspirationRepository
import com.example.snapconnect.data.remote.LangchainApi
import com.example.snapconnect.data.remote.VisionApi
import com.example.snapconnect.data.repository.*

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    // TODO: Replace with your actual Supabase URL and API key
    private const val SUPABASE_URL = "https://ngrbhordabshfvlbqmzu.supabase.co"
    private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5ncmJob3JkYWJzaGZ2bGJxbXp1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTA3MDM3MzAsImV4cCI6MjA2NjI3OTczMH0.0Nse10raADtK8pU3V45hUKdQT4tyeYmvMLMw4P8MYvI"
    
    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_ANON_KEY
        ) {
            install(Auth)
            install(Postgrest)
            install(Storage)
            install(Realtime)
        }
    }
    
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    // ---------------- RAG Backend ----------------

    private const val BACKEND_BASE_URL = "https://snapconnect-backend.onrender.com"
    private const val API_KEY = "RT5PrU6c8dnk5UUaP1dyDIqQjY6KuV2IXXKZvKvkMiz8N2AwrZhHJwYm8GZlCjQWPqaAB9UAMqwYkzPx67DQnx6muHfXscKpmmJVVVp9FWoyWIudwx35Qrv5H3TqwCnm" // TODO move to secure config

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    android.util.Log.d("InspirationHTTP", message)
                }
            }
            level = LogLevel.ALL
        }
    }

    @Provides
    @Singleton
    fun provideInspirationApi(client: HttpClient): InspirationApi =
        InspirationApi(client, BACKEND_BASE_URL, API_KEY)

    @Provides
    @Singleton
    fun provideInspirationRepository(api: InspirationApi): InspirationRepository =
        InspirationRepository(api)

    // ---------- Embedding / RAG ingest ----------

    @Provides
    @Singleton
    fun provideEmbeddingApi(client: HttpClient): com.example.snapconnect.data.remote.EmbeddingApi =
        com.example.snapconnect.data.remote.EmbeddingApi(client, BACKEND_BASE_URL, API_KEY)

    @Provides
    @Singleton
    fun provideEmbeddingRepository(api: com.example.snapconnect.data.remote.EmbeddingApi): com.example.snapconnect.data.repository.EmbeddingRepository =
        com.example.snapconnect.data.repository.EmbeddingRepository(api)

    // Langchain
    @Provides
    @Singleton
    fun provideLangchainApi(client: HttpClient): LangchainApi {
        return LangchainApi(
            client = client,
            baseUrl = BACKEND_BASE_URL,
            apiKey = API_KEY
        )
    }

    @Provides
    @Singleton
    fun provideLangchainRepository(api: com.example.snapconnect.data.remote.LangchainApi): com.example.snapconnect.data.repository.LangchainRepository =
        com.example.snapconnect.data.repository.LangchainRepository(api)

    @Provides
    @Singleton
    fun provideVisionApi(client: HttpClient): VisionApi {
        return VisionApi(
            client = client,
            baseUrl = BACKEND_BASE_URL,
            apiKey = API_KEY
        )
    }
} 