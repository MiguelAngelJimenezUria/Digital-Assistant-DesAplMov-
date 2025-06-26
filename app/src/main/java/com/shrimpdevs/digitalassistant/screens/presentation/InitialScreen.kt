package com.shrimpdevs.digitalassistant.screens.presentation

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.shrimpdevs.digitalassistant.R
import com.shrimpdevs.digitalassistant.ui.theme.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun InitialScreen(
    navigateToLogin: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToEvent: () -> Unit,
    auth: FirebaseAuth,
    context: android.content.Context
) {
    val scope = rememberCoroutineScope()
    val currentContext = LocalContext.current

    // Google Sign In Configuration
    val googleSignInClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("615793980890-9e4lstr61rc63iqb9qbs0fpvds4t352f.apps.googleusercontent.com")
                .requestEmail()
                .build()
        )
    }

    // Google Sign In Launcher
    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                scope.launch {
                    try {
                        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                        auth.signInWithCredential(credential).await()
                        navigateToEvent()
                    } catch (e: Exception) {
                        Toast.makeText(
                            currentContext,
                            "Error: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: ApiException) {
                Toast.makeText(
                    currentContext,
                    "Error de Google Sign In: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // Facebook Sign In Configuration
    val callbackManager = remember { CallbackManager.Factory.create() }

    LaunchedEffect(Unit) {
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    scope.launch {
                        try {
                            val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                            auth.signInWithCredential(credential).await()
                            navigateToEvent()
                        } catch (e: Exception) {
                            Toast.makeText(
                                currentContext,
                                "Error: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                override fun onCancel() {
                    Toast.makeText(
                        currentContext,
                        "Inicio de sesión cancelado",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(
                        currentContext,
                        "Error: ${error.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBlue, Black))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.logo_da),
            contentDescription = "Logo de Aplicacion DA"
        )
        Text(
            text = "Digital Assistant",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navigateToLogin() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp, vertical = 5.dp)
                .shadow(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkText),
            shape = RoundedCornerShape(15.dp),
            contentPadding = PaddingValues(vertical = 15.dp)
        ) {
            Text(text = "Iniciar Sesión", color = Color.White)
        }

        Button(
            onClick = { navigateToSignUp() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp, vertical = 5.dp)
                .shadow(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BlueDark),
            border = BorderStroke(1.dp, DarkBlue),
            shape = RoundedCornerShape(15.dp),
            contentPadding = PaddingValues(vertical = 15.dp)
        ) {
            Text(text = "Crear nueva cuenta", color = Color.White)
        }

        Divider(
            color = Color.White,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 32.dp)
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 100.dp)
                .border(
                    width = 1.dp,
                    color = Gray,
                    shape = RoundedCornerShape(15.dp)
                )
                .background(Color.White, shape = RoundedCornerShape(15.dp)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Botón de Google
            Button(
                onClick = {
                    googleLauncher.launch(googleSignInClient.signInIntent)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp, 12.dp, 6.dp, 12.dp)
                    .shadow(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(15.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google_icon_logo_svgrepo_com),
                    contentDescription = "Logo de Google",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            // Botón de Facebook
            Button(
                onClick = {
                    LoginManager.getInstance().logInWithReadPermissions(
                        context as Activity,
                        listOf("email", "public_profile")
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(6.dp, 12.dp, 12.dp, 12.dp)
                    .shadow(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(15.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.facebook_1_svgrepo_com),
                    contentDescription = "Logo de Facebook",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.weight(2f))
    }
}
