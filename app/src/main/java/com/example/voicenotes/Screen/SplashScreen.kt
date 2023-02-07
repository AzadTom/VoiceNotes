package com.example.voicenotes

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay


@Composable
fun AnimatedSplashScreen(navController: NavHostController) {

    var startAnimation by remember { mutableStateOf(false) }


    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )



    LaunchedEffect(key1 = true){

        startAnimation =true
        delay(4000)
        navController.popBackStack()
        navController.navigate(route = "homescreen")


    }

    Splash(alpha =  alphaAnim.value)




}


@Composable
fun Splash(modifier: Modifier = Modifier, alpha:Float ) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center

    )
    {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = modifier
                    .size(120.dp)
                    .alpha(alpha),
                contentDescription = "logo icon",
                painter = painterResource(id = R.drawable.soundcloud)
            )
            Text("Voice", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 100.sp , modifier = modifier.alpha(alpha))


        }

    }

}