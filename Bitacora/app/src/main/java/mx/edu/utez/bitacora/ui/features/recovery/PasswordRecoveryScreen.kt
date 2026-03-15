package mx.edu.utez.bitacora.ui.features.recovery

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.bitacora.R
import mx.edu.utez.bitacora.data.local.DataStoreManager
import mx.edu.utez.bitacora.ui.components.LoadingScreen
import mx.edu.utez.bitacora.ui.features.login.LoginViewModelFactory
import mx.edu.utez.bitacora.ui.features.recovery.data.PasswordResetRequest

@Composable
fun PasswordRecoveryScreen(viewModel: RecoveryViewModel) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }
    var otpValue by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.statusBarsPadding().fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(state !is RecoveryUiState.Loading){
            Card(
                modifier = Modifier.padding(10.dp)
                    .width(93.dp).height(80.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0B81B7)),
                elevation = CardDefaults.cardElevation(10.dp)
            ){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lucide_book),
                        contentDescription = "Book Icon",
                        modifier = Modifier.size(48.dp),
                        tint = Color.White
                    )
                }
            }

            Text(
                fontSize = 24.sp,
                text = "Bitácora Digital",
                fontWeight = FontWeight.Bold
            )
            Text(
                fontSize = 14.sp,
                text = "Prácticas Profesionales y Servicio Social",
                color = Color(0xFF4A5565)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                modifier = Modifier.align(Alignment.Start),
                fontSize = 24.sp,
                text = "Restablecimiento de contraseña",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        when(state) {
            is RecoveryUiState.Loading -> LoadingScreen()
            is RecoveryUiState.Idle -> {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    onClick = { viewModel.sendResetRequest(PasswordResetRequest(email)) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0B81B7)
                    )
                ) {
                    Text(text = "Solicitar código")
                }
            }
            is RecoveryUiState.CodeEntry -> {
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    fontSize = 14.sp,
                    text = "Hemos enviado un código de verificación, revisa tu bandeja del correo electrónico.",
                    color = Color(0xFF0B81B7)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = otpValue,
                    onValueChange = { if(it.length <= 6) otpValue = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Código") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    onClick = { viewModel.verifyReset(
                        PasswordResetRequest(email, otpValue)
                    )},
                    enabled = otpValue.length == 6,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0B81B7),
                        disabledContainerColor = Color(0xFFF3F4F6),
                        disabledContentColor = Color(0xFF99A1AF)
                    )
                ) {
                    Text(text = "Verificar código")
                }
            }
            is RecoveryUiState.CodeVerified -> {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    visualTransformation = PasswordVisualTransformation(),
                    label = { Text("Nueva Contraseña") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = passwordConfirmation,
                    onValueChange = { passwordConfirmation = it },
                    visualTransformation = PasswordVisualTransformation(),
                    label = { Text("Confirmar Contraseña") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    onClick = { viewModel.changePassword(
                        PasswordResetRequest(email, otpValue, newPassword)
                    )},
                    enabled =
                        newPassword.isNotEmpty() && passwordConfirmation.isNotEmpty() &&
                        passwordConfirmation == newPassword,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0B81B7),
                        disabledContainerColor = Color(0xFFF3F4F6),
                        disabledContentColor = Color(0xFF99A1AF)
                    )
                ) {
                    Text(text = "Restablecer contraseña")
                }
            }
            is RecoveryUiState.Error -> Toast.makeText(context, (state as RecoveryUiState.Error).message, Toast.LENGTH_SHORT).show()
        }

    }
}

@Composable
@Preview(showBackground = true)
fun RecoveryPreview() {
    PasswordRecoveryScreen(
        viewModel = viewModel(
            factory = RecoveryViewModelFactory(
                dataStoreManager = DataStoreManager(LocalContext.current)
            )
        )
    )
}