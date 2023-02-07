package com.example.voicenotes

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.voicenotes.Room.Voice
import com.example.voicenotes.Viewmodel.VoiceViewmodel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VoiceScreen(
    modifier: Modifier = Modifier,
    default : ()-> Unit,
    navHostController: NavHostController,
    outputText: String,
    speechTotext: (context: Context) -> Unit, textToSpeech: (String) -> Unit, viewmodel: VoiceViewmodel
) {


    val context = LocalContext.current





    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        Text(
            text = "VOICE NOTES", fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(shape = CircleShape)
                .background(Color.Black)
                .padding(12.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.speaker),
            contentDescription = "voice",
            modifier = modifier
                .padding(end = 20.dp)
                .size(23.dp)
                .align(Alignment.End)
                .clickable {

                    textToSpeech(outputText)


                }


        )
        Text(
            modifier = modifier.padding(16.dp),
            text = outputText,
            fontWeight = FontWeight.Light,
            color = Color(0xff1f1f29),
            textAlign = TextAlign.Center
        )

        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = "Speak something", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = modifier.height(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.mic),
                    colorFilter = ColorFilter.tint(Color.White),
                    contentDescription = "mic",
                    modifier = modifier
                        .padding(10.dp)
                        .size(80.dp)
                        .clip(shape = CircleShape)
                        .background(Color(0xFF9EECFF))
                        .padding(12.dp)
                        .clickable(


                            onClick = {
                                //now you speak for voice to text guys
                                speechTotext(context)
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }


                        )


                )

                Button(colors = ButtonDefaults.buttonColors(Color.Black), onClick = {

                    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a")
                    val current = LocalDateTime.now().format(formatter)
                    val voice = Voice(0, outputText, current.toString().uppercase(Locale.ROOT))

                    viewmodel.insertVoice(voice)
                    default.invoke()
                    navHostController.popBackStack()

                }) {

                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = null
                    )
                    Text(text = "add task")

                }

            }

        }

    }


}


