package com.example.voicenotes.Room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VoiceDao {


    @Query("SELECT * FROM Voice")
     fun getAllData():Flow<List<Voice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVoice(voice: Voice)

    @Delete
    suspend fun deletevoice(voice:Voice)





}