package com.example.voicenotes.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Voice::class], version = 1, exportSchema = false)
abstract class VoiceDatabase():RoomDatabase(){



    abstract fun VoiceDao() :VoiceDao




}
