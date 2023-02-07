package com.example.voicenotes


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.voicenotes.Screen.HomeScreen
import com.example.voicenotes.Viewmodel.VoiceViewmodel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {

     var message by mutableStateOf("Tap on Mic")
    private lateinit var textToSpeech: TextToSpeech


    @Inject
     lateinit var viewmodel: VoiceViewmodel


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Myapp()
        }

        textToSpeech = TextToSpeech(this, this)


    }


    @SuppressLint("CoroutineCreationDuringComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Myapp() {


        val navController = rememberNavController()


        NavHost(navController = navController, startDestination = "splash") {

            composable(route = "splash") {

                AnimatedSplashScreen(navController)

            }

            composable(route = "homescreen") {

                HomeScreen(
                    navController = navController,
                    viewmodel = viewmodel,
                    onspeak = {
                        Speak(it)
                    },
                    ondelete = { voice -> viewmodel.deleteVoice(voice) }

                )


            }



            composable(route = "voicescreen") {

                VoiceScreen(outputText = message,
                    navHostController = navController,
                    speechTotext = {
                        getSpeechInput(it)

                    }, textToSpeech = {

                        Speak(it)



                    }, viewmodel = viewmodel , default = {

                        message = "Tap on Mic"
                    })
            }

        }


    }



    private fun Speak(text: String) {

        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }


    // on below line we are creating a method
    // to get the speech input from user.
    private fun getSpeechInput(context: Context) {
        // on below line we are checking if speech
        // recognizer intent is present or not.
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            // if the intent is not present we are simply displaying a toast message.
            Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
        } else {
            // on below line we are calling a speech recognizer intent
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            // on the below line we are specifying language model as language web search
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
            )

            // on below line we are specifying extra language as default english language
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.forLanguageTag("hi-IN"))

            // on below line we are specifying prompt as Speak something
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Something")

            // at last we are calling start activity
            // for result to start our activity.
            startActivityForResult(intent, 101)
        }
    }


    // on below line we are calling our on activity result method to get the output.
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // on below line we are checking if the request
        // code is same and result code is ok
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            // if the condition is satisfied we are getting
            // the data from our string array list in our result.
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            // on below line we are setting result
            // in our output text method.
            message = result?.get(0).toString()

        }
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            val res = textToSpeech.setLanguage(Locale.forLanguageTag("hi-IN"))

            if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language is not supported!", Toast.LENGTH_SHORT).show()

            } else {


            }

        } else {
            Toast.makeText(this, "Failed to load!", Toast.LENGTH_SHORT).show()

        }

    }

    override fun onDestroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroy()

    }


}




