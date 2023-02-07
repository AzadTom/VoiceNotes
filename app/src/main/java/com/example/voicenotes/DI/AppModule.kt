package com.example.voicenotes.DI

import android.content.Context
import androidx.room.Room
import com.example.voicenotes.Repositery.VoiceRepositery
import com.example.voicenotes.Room.VoiceDao
import com.example.voicenotes.Room.VoiceDatabase
import com.example.voicenotes.Viewmodel.VoiceViewmodel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    //    1. Database instance
    @Singleton
    @Provides
    fun ProvideDatabaseInstance(@ApplicationContext appcontext: Context): VoiceDatabase {

        return Room.databaseBuilder(context = appcontext, VoiceDatabase::class.java, "Voice")
            .build()

    }


//    2. Dao instnace

    @Singleton
    @Provides
    fun ProvideVoiceDaoInstance(voiceDatabase: VoiceDatabase): VoiceDao {

        return voiceDatabase.VoiceDao()


    }

    //    3. Repositery instance
    @Singleton
    @Provides
    fun ProvidesVoiceRepositeryInstance(voiceDao: VoiceDao): VoiceRepositery {


        return VoiceRepositery(voiceDao)

    }

    //    4. Viewmodel instance
    @Singleton
    @Provides
    fun ProvidesVoiceViewmodel(voiceRepositery: VoiceRepositery): VoiceViewmodel {

        return  VoiceViewmodel(voiceRepositery)
    }


}