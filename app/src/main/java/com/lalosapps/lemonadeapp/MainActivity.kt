package com.lalosapps.lemonadeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    // Number of times the lemon needs to be squeezed to turn into a glass of lemonade
    var squeezeCount by remember { mutableStateOf(0) }

    // Snack
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var job: Job? = remember { null }
    val snackMessage = stringResource(id = R.string.keep_squeezing)

    Scaffold(scaffoldState = scaffoldState) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            when (currentStep) {
                1 -> TextAndImageStep(
                    text = R.string.lemon_tree_instructions,
                    drawable = R.drawable.lemon_tree,
                    contentDesc = R.string.lemon_tree_content_description
                ) {
                    currentStep = 2
                    squeezeCount = (2..4).random()
                }
                2 -> TextAndImageStep(
                    text = R.string.lemon_instructions,
                    drawable = R.drawable.lemon_squeeze,
                    contentDesc = R.string.lemon_content_description
                ) {
                    job?.cancel()
                    if (--squeezeCount == 0) {
                        currentStep = 3
                    } else {
                        job = scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(snackMessage)
                        }
                    }
                }
                3 -> TextAndImageStep(
                    text = R.string.glass_of_lemonade_instructions,
                    drawable = R.drawable.lemon_drink,
                    contentDesc = R.string.glass_of_lemonade_content_description
                ) {
                    currentStep = 4
                }
                4 -> TextAndImageStep(
                    text = R.string.empty_glass_instructions,
                    drawable = R.drawable.lemon_restart,
                    contentDesc = R.string.empty_glass_content_description
                ) {
                    currentStep = 1
                }
            }
        }
    }

}

@Composable
fun TextAndImageStep(
    @StringRes text: Int,
    @DrawableRes drawable: Int,
    @StringRes contentDesc: Int,
    onImageClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = text), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = drawable),
            contentDescription = stringResource(id = contentDesc),
            modifier = Modifier
                .wrapContentSize()
                .clickable(onClick = onImageClick)
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