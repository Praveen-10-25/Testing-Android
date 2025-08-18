package com.example.permission.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun PermissionScreen() {
    val context = LocalContext.current

    val permissionsToRequest = buildList {
        add(Manifest.permission.ACCESS_FINE_LOCATION)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            add(Manifest.permission.READ_MEDIA_IMAGES)
            add(Manifest.permission.READ_MEDIA_VIDEO)
        }
    }

    var grantedPermissions by remember { mutableStateOf(setOf<String>()) }
    var permanentlyDenied by remember { mutableStateOf(false) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        val granted = results.filterValues { it }.keys
        grantedPermissions = grantedPermissions + granted

        if (results.values.contains(false)) {
            permanentlyDenied = true
        }
    }

    val allGranted = permissionsToRequest.all { it in grantedPermissions }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        when {
            allGranted -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(20.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(" All Permissions Granted: Internet (auto), Files & Location")
                    }
                }
            }
            permanentlyDenied -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(20.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(" Some permissions permanently denied. Enable them in Settings.")

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(onClick = {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", context.packageName, null)
                            }
                            context.startActivity(intent)
                        }) {
                            Text("Open Settings")
                        }
                    }
                }
            }
            else -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(20.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Permissions missing: Internet (auto), Files, Location")

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(onClick = {
                            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
                        }) {
                            Text("Request Again")
                        }
                    }
                }
            }
        }
    }
}
