package com.aviva.splashscreen_compose.ui

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aviva.splashscreen_compose.R
import kotlinx.coroutines.delay

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val scale: Float by animateFloatAsState(
        targetValue = 1.4f,
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
    )

    val offsetX: Dp by animateDpAsState(
        targetValue = 200.dp,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
    )

    val offsetY: Dp by animateDpAsState(
        targetValue = 100.dp,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
    )

    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController = navController, scale = scale, offsetX = offsetX, offsetY = offsetY)
        }
        composable("main_screen") {
            Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
                Text(
                    text = "Welcome to splash screen",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun SplashScreen(navController: NavController, scale: Float, offsetX: Dp, offsetY: Dp) {
    val scaleAnim = remember { androidx.compose.animation.core.Animatable(scale) }
    LaunchedEffect(key1 = true) {
        scaleAnim.animateTo(targetValue = 0.3f, animationSpec = tween(durationMillis = 1000, easing = { OvershootInterpolator(2f).getInterpolation(it) }))
        delay(3000L)
        navController.navigate("main_screen")
    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.image), contentDescription = "Logo", modifier = Modifier.scale(scaleAnim.value).offset(x = offsetX, y = offsetY))
    }
}