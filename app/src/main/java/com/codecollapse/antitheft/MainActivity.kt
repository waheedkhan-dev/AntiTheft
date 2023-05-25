package com.codecollapse.antitheft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.codecollapse.antitheft.ui.theme.AntiTheftTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AntiTheftTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onResume() {
        super.onResume()
        //  stabilityDetector.startListening()
        //  pocketDetector.start()
    }

    override fun onPause() {
        super.onPause()
        // stabilityDetector.stopListening()
        //   pocketDetector.stop()
    }


}
