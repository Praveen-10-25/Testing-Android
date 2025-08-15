package com.example.localhtml.ui.view

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun UserFormScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    var error by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Age") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Address") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(12.dp))

        if (error.isNotEmpty()) {
            Text(text = error, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                error = when {
                    name.isBlank() -> "Name is required"
                    age.isBlank() -> "Age is required"
                    address.isBlank() -> "Address is required"
                    email.isBlank() -> "Email is required"
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Enter a valid email"
                    phone.isBlank() -> "Phone is required"
                    phone.length != 10 || !phone.all { it.isDigit() } -> "Phone must be 10 digits"
                    else -> ""
                }

                if (error.isNotEmpty()) {
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                } else {
                    val entryTime = URLEncoder.encode(sdf.format(Date()), "UTF-8")
                    val encodedName = URLEncoder.encode(name, "UTF-8")
                    val encodedAge = URLEncoder.encode(age, "UTF-8")
                    val encodedAddress = URLEncoder.encode(address, "UTF-8")
                    val encodedEmail = URLEncoder.encode(email, "UTF-8")
                    val encodedPhone = URLEncoder.encode(phone, "UTF-8")
                    navController.navigate("webview/$encodedName/$encodedAge/$encodedAddress/$encodedEmail/$encodedPhone/$entryTime")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}
