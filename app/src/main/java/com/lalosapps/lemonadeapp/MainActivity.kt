package com.lalosapps.lemonadeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lalosapps.lemonadeapp.ui.theme.LemonadeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeAppTheme {
                LemonApp()
            }
        }
    }
}

@Composable
fun LemonApp() {
    // Current step the app is displaying (remember allows the state to be retained
    // across recompositions).
    var currentStep by remember { mutableStateOf(1) }

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        when (currentStep) {
            1 -> StepOne { currentStep = 2 }
            2 -> StepTwo { currentStep = 3 }
            3 -> StepThree { currentStep = 4 }
            4 -> StepFour { currentStep = 1 }
        }
    }
}

@Composable
fun StepOne(
    onImageClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.lemon_tree_instructions), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.lemon_tree),
            contentDescription = stringResource(id = R.string.lemon_tree_content_description),
            modifier = Modifier
                .wrapContentSize()
                .clickable { onImageClicked() }
                .border(2.dp, Color(105, 205, 216), RoundedCornerShape(4.dp))
                .padding(16.dp)
        )
    }
}

@Composable
fun StepTwo(
    onImageClicked: () -> Unit
) {
    val squeeze = remember { (2..4).random() }
    var squeezeCount by remember { mutableStateOf(0) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.lemon_instructions), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.lemon_squeeze),
            contentDescription = stringResource(id = R.string.lemon_content_description),
            modifier = Modifier
                .wrapContentSize()
                .clickable { if (++squeezeCount == squeeze) onImageClicked() }
                .border(2.dp, Color(105, 205, 216), RoundedCornerShape(4.dp))
                .padding(16.dp)
        )
    }
}

@Composable
fun StepThree(
    onImageClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.glass_of_lemonade_instructions), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.lemon_drink),
            contentDescription = stringResource(id = R.string.glass_of_lemonade_content_description),
            modifier = Modifier
                .wrapContentSize()
                .clickable { onImageClicked() }
                .border(2.dp, Color(105, 205, 216), RoundedCornerShape(4.dp))
                .padding(16.dp)
        )
    }
}

@Composable
fun StepFour(
    onImageClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.empty_glass_instructions), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.lemon_restart),
            contentDescription = stringResource(id = R.string.empty_glass_content_description),
            modifier = Modifier
                .wrapContentSize()
                .clickable { onImageClicked() }
                .border(2.dp, Color(105, 205, 216), RoundedCornerShape(4.dp))
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeAppTheme {
        LemonApp()
    }
}