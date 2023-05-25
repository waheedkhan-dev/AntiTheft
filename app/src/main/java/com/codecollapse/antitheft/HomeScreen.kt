package com.codecollapse.antitheft

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    mainActivityViewModel: MainActivityViewModel = hiltViewModel()
) {
    val currentContext = LocalContext.current
    val pocketRemovalTextState by mainActivityViewModel.pocketRemovalTextState
    val chargerRemovalTextState by mainActivityViewModel.chargerRemovalTextState
    val motionDetectorTextState by mainActivityViewModel.motionDetectorTextState

    val pocketRemovalButtonState by mainActivityViewModel.pocketRemovalButtonState
    val chargerRemovalButtonState by mainActivityViewModel.chargerRemovalButtonState
    val motionDetectorButtonState by mainActivityViewModel.motionDetectorButtonState

    var pocketRemovalButtonEnable by remember {
        mutableStateOf(true)
    }
    var chargerRemovalButtonEnable by remember {
        mutableStateOf(true)
    }
    var motionDetectorButtonEnable by remember {
        mutableStateOf(true)
    }
    mainActivityViewModel.startLockButtonService(currentContext)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = pocketRemovalTextState,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(8.dp)
        )
        Text(
            text = chargerRemovalTextState,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = motionDetectorTextState,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(enabled = pocketRemovalButtonEnable, onClick = {
            if (pocketRemovalButtonEnable) {
                pocketRemovalButtonEnable = false
                mainActivityViewModel.startPocketDetectorService()
                mainActivityViewModel.changePocketRemovalState("Observing changes")
            }

        }) {
            Text(text = pocketRemovalButtonState)
        }

        Button(enabled = chargerRemovalButtonEnable, onClick = {
            if (chargerRemovalButtonEnable) {
                chargerRemovalButtonEnable = false
                mainActivityViewModel.startChargerService(currentContext)
                mainActivityViewModel.changeChargerRemovalState("Observing changes")
            }
        }) {
            Text(text = chargerRemovalButtonState)
        }

        Button(enabled = motionDetectorButtonEnable, onClick = {
            if (motionDetectorButtonEnable) {
                motionDetectorButtonEnable = false
                mainActivityViewModel.startMotionDetectorService()
                mainActivityViewModel.changeMotionDetectorState("Observing changes")
            }
        }) {
            Text(text = motionDetectorButtonState)
        }
    }
}
