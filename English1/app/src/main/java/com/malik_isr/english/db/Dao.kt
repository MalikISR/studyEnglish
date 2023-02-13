package com.malik_isr.english.db

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.malik_isr.english.entities.Sentences
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    suspend fun insertSentence(sentence: Sentences){
        Log.d("My", "${sentence.id} ${sentence.English} ${sentence.Russian}")
    }
    @Query ("SELECT * FROM sentences")
    fun getAllSentences(): Flow<List<Sentences>>
}