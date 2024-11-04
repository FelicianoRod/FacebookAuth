//package com.example.facebookauth
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.facebookauth.ui.theme.FacebookAuthTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            FacebookAuthTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    FacebookAuthTheme {
//        Greeting("Android")
//    }
//}
package com.example.facebookauth

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.facebookauth.ui.CustomLoginButton
import com.example.facebookauth.ui.LoginViewModel
import com.example.facebookauth.ui.ProfileViewState
import com.example.facebookauth.ui.WrappedLoginButton
import com.example.facebookauth.ui.theme.FacebookAuthTheme
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
//import com.jetpack.facebookintegration.WrappedLoginButton
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: LoginViewModel by viewModels()
        setContent {
            FacebookAuthTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    printHashKey(LocalContext.current)

                    val profileViewState by viewModel.profileViewState.observeAsState(
                        ProfileViewState()
                    )

                    SampleView(profileViewState, login, logout)
                }
            }
        }
    }

    private val login = {
        LoginManager.getInstance().logIn(this, CallbackManager.Factory.create(), listOf("email"))
    }

    private val logout = {
        LoginManager.getInstance().logOut()
    }
}

@Composable
fun SampleView(profileViewState: ProfileViewState, login: () -> Unit, logout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomLoginButton(
            profile = profileViewState.profile,
            login = { login() },
            logout = { logout() }
        )

        Spacer(modifier = Modifier.height(15.dp))

        WrappedLoginButton()

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = profileViewState.profile?.name ?: "Logged Out"
        )
    }
}

fun printHashKey(context: Context) {
    try {
        val info: PackageInfo = context.packageManager
            .getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
        for (signature in info.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            val hashKey: String = String(Base64.encode(md.digest(), 0))
            Log.d("hashkey", "Hash Key: $hashKey")
        }
    } catch (e: NoSuchAlgorithmException) {
        Log.e("Error", "${e.localizedMessage}")
    } catch (e: Exception) {
        Log.e("Exception", "${e.localizedMessage}")
    }
}















