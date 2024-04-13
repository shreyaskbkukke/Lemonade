package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonApp() {
    var currentStep by remember { mutableIntStateOf(0) }
    var squeezeCount by remember { mutableIntStateOf(0) }
    var requiredSqueezeCount by remember { mutableIntStateOf(generateRandomSqueezeCount()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (currentStep) {
            0 -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.welcome_user),
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                    )
                    Button(
                        onClick = {
                            currentStep = 1
                        }
                    ) {
                        Text(
                            stringResource(R.string.start),
                            fontSize = 18.sp
                        )
                    }
                }
            }
            1 -> {
                ImageAndTextCompose(
                    imageResource = R.drawable.lemon_tree,
                    imageContentDescription = R.string.lemon_tree_content_description,
                    nextStep = 2,
                    textResource = R.string.first_title
                ) {
                    currentStep = it
                }
            }

            2 -> {
                ImageAndTextCompose(
                    imageResource = R.drawable.lemon_squeeze,
                    imageContentDescription = R.string.lemon_content_description,
                    nextStep = 3,
                    textResource = R.string.second_title
                ) {
                    if (squeezeCount < requiredSqueezeCount) {
                        squeezeCount++
                        if (squeezeCount == requiredSqueezeCount) {
                            currentStep = it
                            squeezeCount = 0
                            requiredSqueezeCount = generateRandomSqueezeCount()
                        }
                    }
                }
            }

            3 -> {
                ImageAndTextCompose(
                    imageResource = R.drawable.lemon_drink,
                    imageContentDescription = R.string.glass_of_lemonade_content_description,
                    nextStep = 4,
                    textResource = R.string.third_title
                ) {
                    currentStep = it
                }
            }

            4 -> {
                ImageAndTextCompose(
                    imageResource = R.drawable.lemon_restart,
                    imageContentDescription = R.string.empty_glass_content_description,
                    nextStep = 1,
                    textResource = R.string.fourth_title
                ) {
                    currentStep = 0
                }
            }
        }
    }
}

@Composable
fun ImageAndTextCompose(
    imageResource: Int,
    imageContentDescription: Int,
    nextStep: Int,
    textResource: Int,
    onImageClicked: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = stringResource(id = imageContentDescription),
            modifier = Modifier
                .padding(4.dp)
                .size(width = 250.dp, height = 250.dp)
                .background(
                    color = Color(0xFFB5F3FF),
                    shape = RoundedCornerShape(30.dp)
                )
                .clickable {
                    onImageClicked(nextStep)
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = textResource),
            fontSize = 18.sp
        )
    }
}

fun generateRandomSqueezeCount(): Int {
    return Random.nextInt(2, 4)
}