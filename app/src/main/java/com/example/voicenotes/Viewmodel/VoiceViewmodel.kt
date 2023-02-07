package com.example.voicenotes.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voicenotes.Repositery.VoiceRepositery
import com.example.voicenotes.Room.Voice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoiceViewmodel @Inject constructor(private val voiceRepositery: VoiceRepositery) :
    ViewModel() {


    private var allnotes = MutableLiveData<List<Voice>>()
    val list: LiveData<List<Voice>> get() = allnotes
    private var notes = listOf<Voice>()


    init {


        getAllData()


    }


    fun getAllData() = viewModelScope.launch(Dispatchers.IO) {


        voiceRepositery.getAllData().collect() {


            allnotes.postValue(it)
            notes = it


        }

    }

    fun Filterlist(query: String) {


            val filteredList = ArrayList<Voice>()


            notes.forEach { note ->

                if (note.message.lowercase().contains(query.lowercase())) {
                    filteredList.add(note)
                }

            }

        if (filteredList.isNotEmpty()){

            allnotes.postValue(filteredList)

        }

        else{

            allnotes.postValue(notes)

        }




    }


    fun insertVoice(voice: Voice) = viewModelScope.launch(Dispatchers.IO) {

        voiceRepositery.insertVoice(voice = voice)


    }

    fun deleteVoice(voice: Voice) = viewModelScope.launch(Dispatchers.IO) {

        voiceRepositery.deleteVoice(voice = voice)


    }


}