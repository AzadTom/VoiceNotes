package com.example.voicenotes.Repositery

import com.example.voicenotes.Room.Voice
import com.example.voicenotes.Room.VoiceDao
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class VoiceRepositery @Inject constructor(private val voiceDao: VoiceDao) {


        fun getAllData() = voiceDao.getAllData()


       suspend fun insertVoice(voice: Voice) = voiceDao.insertVoice(voice)

       suspend fun deleteVoice(voice: Voice) = voiceDao.deletevoice(voice)








}