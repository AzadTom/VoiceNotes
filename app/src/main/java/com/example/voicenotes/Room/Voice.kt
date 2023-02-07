package com.example.voicenotes.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity
data class Voice(

     @PrimaryKey(autoGenerate = true)
      @Nonnull
     @ColumnInfo("id") var id: Int,

     @Nonnull
     @ColumnInfo("message") var message: String,
     @Nonnull
     @ColumnInfo("date") var date: String


)
