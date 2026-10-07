package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.User
import com.example.ui.MainViewModel
import com.example.ui.components.GamingButton
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    viewModel: MainViewModel,
    onSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var isLogin by remember { mutableStateOf(true) }
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isLogin) "LOGIN" else "CREATE ACCOUNT", color = White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundColor)
            )
        },
        containerColor = BackgroundColor
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "DX ESPORTS",
                color = NeonOrange,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(vertical = 32.dp)
            )

            if (!isLogin) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = White,
                        focusedTextColor = White,
                        unfocusedBorderColor = White.copy(alpha = 0.3f),
                        focusedBorderColor = NeonOrange
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = White,
                        focusedTextColor = White,
                        unfocusedBorderColor = White.copy(alpha = 0.3f),
                        focusedBorderColor = NeonOrange
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email or Mobile") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = White,
                    focusedTextColor = White,
                    unfocusedBorderColor = White.copy(alpha = 0.3f),
                    focusedBorderColor = NeonOrange
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (!isLogin) {
                OutlinedTextField(
                    value = mobile,
                    onValueChange = { mobile = it },
                    label = { Text("Mobile Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = White,
                        focusedTextColor = White,
                        unfocusedBorderColor = White.copy(alpha = 0.3f),
                        focusedBorderColor = NeonOrange
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = White,
                    focusedTextColor = White,
                    unfocusedBorderColor = White.copy(alpha = 0.3f),
                    focusedBorderColor = NeonOrange
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (!isLogin) {
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = White,
                        focusedTextColor = White,
                        unfocusedBorderColor = White.copy(alpha = 0.3f),
                        focusedBorderColor = NeonOrange
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            error?.let {
                Text(it, color = NeonRed, modifier = Modifier.padding(vertical = 8.dp))
            }

            GamingButton(
                text = if (isLogin) "Login" else "Register",
                onClick = {
                    if (isLogin) {
                        viewModel.login(email, password, onSuccess, { error = it })
                    } else {
                        if (password != confirmPassword) {
                            error = "Passwords do not match"
                        } else {
                            viewModel.register(
                                User(name = name, username = username, email = email, mobile = mobile, password = password),
                                onSuccess,
                                { error = it }
                            )
                        }
                    }
                }
            )

            TextButton(onClick = { isLogin = !isLogin; error = null }) {
                Text(
                    if (isLogin) "New to DX ESPORTS? Create Account" else "Already have an account? Login",
                    color = White.copy(alpha = 0.7f)
                )
            }
        }
    }
}
