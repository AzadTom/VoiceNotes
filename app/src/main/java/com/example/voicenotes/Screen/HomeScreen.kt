package com.example.voicenotes.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.voicenotes.R
import com.example.voicenotes.Room.Voice
import com.example.voicenotes.Viewmodel.VoiceViewmodel
import java.util.*


@Composable
fun HomeScreen(
    navController: NavHostController,
    viewmodel: VoiceViewmodel,
    onspeak: (String) -> Unit,
    ondelete: (Voice) -> Unit
) {



    val allnotes = viewmodel.list.observeAsState().value





    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 18.dp, end = 18.dp)
    ) {

        Spacer(modifier = Modifier.height(18.dp))

        Text(text = "Good morning,Geeks", fontWeight = FontWeight.Bold)
        Text(text = "We wish you have a good day!", fontWeight = FontWeight.Light)

        Spacer(modifier = Modifier.height(18.dp))

        Search(viewmodel)


        Spacer(modifier = Modifier.height(18.dp))

        Button(colors = ButtonDefaults.buttonColors(Color.Black), onClick = {


            navController.navigate(route = "voicescreen")


        }) {

            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = null,
                modifier = Modifier
            )
            Text(text = "ADD TASK")

        }


        Spacer(modifier = Modifier.height(18.dp))


        Text(text = "TASK", fontWeight = FontWeight.Bold)
        LazyColumn {

                if (!allnotes.isNullOrEmpty()) {

                    items(allnotes) { list ->

                        Item(
                            Date = list.date,
                            message = list.message,
                            onspeak = { onspeak(list.message) },
                            ondelete = { ondelete(list) })
                        Spacer(modifier = Modifier.height(12.dp))


                    }


                }
            }





    }


}


@Composable
fun Item(Date: String, message: String, onspeak: () -> Unit, ondelete: () -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Box(modifier = Modifier) {

            Row() {
                Icon(
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            onspeak.invoke()
                        },
                    contentDescription = "speaker",
                    painter = painterResource(id = R.drawable.speaker),
                    tint = Color.Black
                )

                Column(modifier = Modifier.fillMaxWidth(0.9f)) {

                    Text(
                        text = message.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                    Text(
                        text = Date.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        fontWeight = FontWeight.Light,
                        fontSize = 10.sp
                    )

                }

            }
        }


        Icon(
            modifier = Modifier
                .size(25.dp)
                .clickable {
                    ondelete.invoke()
                },
            contentDescription = "delete",
            painter = painterResource(id = R.drawable.delete),
            tint = Color.Black
        )


    }

}


@Composable
fun Search(viewmodel: VoiceViewmodel) {





    var query: String by rememberSaveable { mutableStateOf("") }
    var showClearIcon by rememberSaveable { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    if (query.isEmpty()) {
        showClearIcon = false
    } else if (query.isNotEmpty()) {
        showClearIcon = true
    }

    TextField(
        value = query,
        onValueChange = { onQueryChanged ->
            query = onQueryChanged

            if (onQueryChanged.isNotEmpty())
            {
                viewmodel.Filterlist(query)

            }else
            {
                viewmodel.Filterlist(query)

            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "Search icon"
            )
        },
        trailingIcon = {

            if (showClearIcon) {
                IconButton(onClick = {
                    query = ""

                    if (query.isEmpty())
                    {
                        viewmodel.Filterlist(query)
                    }


                }) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "Clear icon"
                    )
                }
            }
        },
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = { Text(text = stringResource(R.string.hint_search_query)) },
        textStyle = MaterialTheme.typography.bodyMedium,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .border(width = 1.dp, color = Color.Black, shape = CircleShape)


    )


}