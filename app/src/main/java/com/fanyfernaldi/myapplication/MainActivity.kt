package com.fanyfernaldi.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.fanyfernaldi.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private var myPhoneNumber = ""
    
    @SuppressLint("MissingPermission")
    private val allPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.getOrDefault(
                Manifest.permission.READ_SMS,
                false
            ) || permissions.getOrDefault(
                Manifest.permission.READ_PHONE_NUMBERS,
                false
            ) || permissions.getOrDefault(
                Manifest.permission.READ_PHONE_STATE,
                false
            )
        ) {
            val telMgr: TelephonyManager =
                this.applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            myPhoneNumber = telMgr.line1Number
            Log.i("MainActivity", "My PhoneNumber: $myPhoneNumber")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        allPermissionRequest.launch(
            arrayOf(
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.READ_PHONE_STATE,
            )
        )
        
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}
