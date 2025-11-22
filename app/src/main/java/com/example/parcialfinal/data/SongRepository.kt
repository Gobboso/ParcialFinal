package com.example.parcialfinal.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class Song(
    val author: String = "",
    val title: String = "",
    val album: String = "",
    val year: String = ""
)

class SongRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("songs")

    suspend fun addSong(song: Song): Result<String> {
        return try {
            val doc = collection.add(song).await()
            Result.success(doc.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
