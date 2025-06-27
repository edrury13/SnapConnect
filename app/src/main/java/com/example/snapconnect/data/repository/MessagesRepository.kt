package com.example.snapconnect.data.repository

import com.example.snapconnect.data.model.Group
import com.example.snapconnect.data.model.MediaType
import com.example.snapconnect.data.model.Message
import com.example.snapconnect.data.model.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.add
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessagesRepository @Inject constructor(
    private val supabase: SupabaseClient
) {
    
    private val messagesCache = MutableStateFlow<Map<String, List<Message>>>(emptyMap())
    
    /**
     * Create a group chat with a custom name and member list (including the current user).
     */
    suspend fun createGroup(name: String, memberIds: List<String>, avatarUrl: String? = null): Result<Group> {
        return try {
            val currentUserId = supabase.auth.currentUserOrNull()?.id
                ?: return Result.failure(Exception("User not authenticated"))

            // Ensure current user is in the members list
            val finalMembers = if (memberIds.contains(currentUserId)) memberIds else memberIds + currentUserId

            // Create group
            val group = supabase.from("groups")
                .insert(
                    buildJsonObject {
                        put("name", name)
                        put("creator_id", currentUserId)
                        putJsonArray("member_ids") {
                            finalMembers.forEach { add(it) }
                        }
                        avatarUrl?.let { put("avatar_url", it) }
                    }
                )
                .decodeSingle<Group>()

            Result.success(group)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createDirectMessageGroup(friendId: String): Result<Group> {
        return try {
            val currentUserId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            // Check if direct message group already exists
            // First get all groups the current user is in
            val userGroups = supabase.from("groups")
                .select() {
                    filter {
                        contains("member_ids", listOf(currentUserId))
                    }
                }
                .decodeList<Group>()
            
            // Then filter for direct messages with the friend
            val existingGroups = userGroups.filter { group ->
                group.memberIds.size == 2 && 
                group.memberIds.contains(currentUserId) && 
                group.memberIds.contains(friendId)
            }
            
            if (existingGroups.isNotEmpty()) {
                return Result.success(existingGroups.first())
            }
            
            // Create new direct message group
            val group = supabase.from("groups")
                .insert(
                    buildJsonObject {
                        put("name", "Direct Message") // Will be overridden in UI
                        put("creator_id", currentUserId)
                        putJsonArray("member_ids") {
                            add(currentUserId)
                            add(friendId)
                        }
                    }
                )
                .decodeSingle<Group>()
            
            Result.success(group)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun sendMessage(
        groupId: String,
        content: String,
        mediaUrl: String? = null,
        mediaType: MediaType? = null
    ): Result<Message> {
        return try {
            val senderId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            val messageData = buildJsonObject {
                put("group_id", groupId)
                put("sender_id", senderId)
                put("content", content)
                mediaUrl?.let { put("media_url", it) }
                mediaType?.let { put("media_type", it.name) }
            }
            
            // Insert message and get the result
            val message = supabase.from("messages")
                .insert(messageData) {
                    select() // This tells Supabase to return the inserted record
                }
                .decodeSingle<Message>()
            
            Result.success(message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getMessages(groupId: String, limit: Int = 50): Result<List<Message>> {
        return try {
            val messages = supabase.from("messages")
                .select() {
                    filter {
                        eq("group_id", groupId)
                    }
                    order(column = "created_at", order = Order.DESCENDING)
                    limit(limit.toLong())
                }
                .decodeList<Message>()
                .reversed() // Reverse to get chronological order
            
            // Update cache
            messagesCache.value = messagesCache.value + (groupId to messages)
            
            Result.success(messages)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getMyGroups(): Result<List<Pair<Group, Message?>>> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            // Get all groups where user is a member
            val groups = supabase.from("groups")
                .select() {
                    filter {
                        contains("member_ids", listOf(userId))
                    }
                }
                .decodeList<Group>()
            
            // Get last message for each group
            val groupsWithLastMessage = groups.map { group ->
                val lastMessageResult = supabase.from("messages")
                    .select() {
                        filter {
                            eq("group_id", group.id)
                        }
                        order(column = "created_at", order = Order.DESCENDING)
                        limit(1)
                    }
                    .decodeList<Message>()
                
                group to lastMessageResult.firstOrNull()
            }
            
            // Sort by last message time
            val sortedGroups = groupsWithLastMessage.sortedByDescending { (_, lastMessage) ->
                lastMessage?.createdAt
            }
            
            Result.success(sortedGroups)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getGroupMembers(group: Group): Result<List<User>> {
        return try {
            // For small groups, fetch members individually
            val members = mutableListOf<User>()
            
            for (memberId in group.memberIds) {
                val userResult = supabase.from("users")
                    .select() {
                        filter {
                            eq("id", memberId)
                        }
                    }
                    .decodeList<User>()
                
                userResult.firstOrNull()?.let { members.add(it) }
            }
            
            Result.success(members)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getMessagesRealtime(groupId: String): Flow<List<Message>> {
        return flow {
            // First, get initial messages
            val initialMessages = getMessages(groupId)
            if (initialMessages.isSuccess) {
                emit(initialMessages.getOrDefault(emptyList()))
            }
            
            // Create and subscribe to channel for this group
            val channel = supabase.realtime.channel("messages_$groupId")
            
            try {
                // Set up the postgres change listener
                val changeFlow = channel.postgresChangeFlow<PostgresAction>(schema = "public") {
                    table = "messages"
                    filter = "group_id=eq.$groupId"
                }
                
                // Subscribe to the channel
                channel.subscribe()
                
                // Listen for changes
                changeFlow.collect { change ->
                    // Fetch fresh messages when any change occurs
                    val result = getMessages(groupId)
                    if (result.isSuccess) {
                        emit(result.getOrDefault(emptyList()))
                    }
                }
            } finally {
                // Unsubscribe from channel when flow is cancelled
                channel.unsubscribe()
                supabase.realtime.removeChannel(channel)
            }
        }
    }
    
    fun getGroupsRealtime(): Flow<List<Pair<Group, Message?>>> {
        return flow {
            val userId = supabase.auth.currentUserOrNull()?.id ?: return@flow
            
            // Initial load
            val initialGroups = getMyGroups()
            if (initialGroups.isSuccess) {
                emit(initialGroups.getOrDefault(emptyList()))
            }
            
            // Create and subscribe to channel
            val channel = supabase.realtime.channel("groups_messages_changes")
            
            try {
                // Set up the postgres change listener for all messages
                val changeFlow = channel.postgresChangeFlow<PostgresAction>(schema = "public") {
                    table = "messages"
                }
                
                // Subscribe to the channel
                channel.subscribe()
                
                // Listen for changes
                changeFlow.collect {
                    // Refresh groups list when any message is inserted
                    val groups = getMyGroups()
                    if (groups.isSuccess) {
                        emit(groups.getOrDefault(emptyList()))
                    }
                }
            } finally {
                // Unsubscribe from channel when flow is cancelled
                channel.unsubscribe()
                supabase.realtime.removeChannel(channel)
            }
        }
    }
    
    suspend fun markMessagesAsRead(groupId: String) {
        // This could be implemented with a read receipts table if needed
        // For now, we'll just track locally
    }
} 