package com.example.facebookauth.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.facebook.login.widget.LoginButton

@Composable
fun WrappedLoginButton() {
    AndroidView(
        factory = {
            context -> LoginButton(context)
        }
    )
}