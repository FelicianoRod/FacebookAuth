package com.example.facebookauth.ui

// Button

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.facebook.Profile

@Composable
fun CustomLoginButton(profile: Profile?, login: () -> Unit, logout: () -> Unit) {
    val buttonText = if (profile == null) {
        "Continue with Facebook"
    } else {
        "Log out"
    }
    val onClick = if (profile == null) {
        login
    } else {
        logout
    }
    Button(
        onClick = {
            onClick
        }
    ) {
        Text(
            text = buttonText
        )
    }
}
