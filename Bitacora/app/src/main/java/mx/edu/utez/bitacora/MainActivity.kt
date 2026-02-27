package mx.edu.utez.bitacora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mx.edu.utez.bitacora.data.local.DataStoreManager
import mx.edu.utez.bitacora.navigation.AppNavigation
import mx.edu.utez.bitacora.ui.theme.BitacoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dataStoreManager = DataStoreManager(applicationContext)

        setContent {
            BitacoraTheme(dynamicColor = false) {
                AppNavigation(dataStoreManager = dataStoreManager)
            }
        }
    }
}