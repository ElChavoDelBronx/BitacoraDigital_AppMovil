package mx.edu.utez.bitacora.ui.features.evidences

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.bitacora.ui.components.SpinnerDropdown
import mx.edu.utez.bitacora.R
import mx.edu.utez.bitacora.ui.features.evidences.components.AttachedFileItem
import mx.edu.utez.bitacora.ui.components.SelectableButton
import mx.edu.utez.bitacora.ui.features.evidences.components.InputGroup
import mx.edu.utez.bitacora.ui.features.evidences.data.EvidenceRequest

enum class EvidenceType(@StringRes val labelRestId: Int){
    Text(R.string.evidence_type_text),
    File(R.string.evidence_type_file)
}

@Composable
fun EvidenceScreen(
    viewModel: EvidenceViewModel
) {
    val context = LocalContext.current
    val state by viewModel.uploadState.collectAsState()
    val tasks by viewModel.tasks.collectAsState()
    val formState by viewModel.formUiState.collectAsState()
    val scrollState = rememberScrollState()
    val pickFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris ->
            // Actualiza la lista de uris sin duplicar archivos
            viewModel.onSelectedFilesChanged(uris)
        }
    )

    LaunchedEffect(state) {
        if(state is UploadState.Error) {
            Toast.makeText(context, (state as UploadState.Error).message, Toast.LENGTH_SHORT).show()
        }
    }
    Column(
        modifier = Modifier.statusBarsPadding()
            .fillMaxWidth()
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ){
        Column {
            Text(
                text = "Registrar Evidencia",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                
            )
            Text(
                text = "Imágenes o archivos adjuntos",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        InputGroup(label = "TAREA ASOCIADA") {
            SpinnerDropdown(
                text = "Selecciona una tarea",
                list = tasks,
                onSelectedItem = { selected ->
                    viewModel.onSelectedTaskChanged(selected)
                }
            )
        }
        InputGroup(label = "horas trabajadas") {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = formState.workedHours,
                onValueChange = { newValue ->
                    if(newValue.all { it.isDigit() } && newValue.length <= 2) viewModel.onWorkedHoursChanged(newValue) },
                label = { Text(text = "")  },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        InputGroup(label = "Archivos adjuntos") {
            // Alterna entre botón para adjuntar el primer archivo y botón para agregar más archivos
            if(formState.selectedFileUris.isEmpty()){
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    onClick = { pickFileLauncher.launch(
                        arrayOf("application/pdf", "application/msword", "text/plain", "image/png", "image/jpeg")
                    ) },
                    colors = ButtonDefaults.buttonColors(containerColor =  Color.Transparent)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_lucide_upload),
                            contentDescription = null,
                            tint = Color(0xFF155DFC)
                        )
                        Text(text = "Subir archivos", fontSize = 16.sp)
                        Text(text = "Imágenes, PDF, documentos", color = MaterialTheme.colorScheme.onTertiary, fontSize = 14.sp)
                    }
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    formState.selectedFileUris.forEach { uri ->
                      AttachedFileItem(
                          fileUri = uri,
                          onRemoveClick = {
                              viewModel.onRemoveFile(uri)
                          }
                      )
                    }
                    IconButton(
                        onClick = { pickFileLauncher.launch(
                        arrayOf("application/pdf", "application/msword", "text/plain", "image/png", "image/jpeg")
                        )}
                    ){
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar archivo",
                            tint = Color.DarkGray,
                            modifier = Modifier.padding(4.dp)
                        )
                    }

                }
            }
        }
        InputGroup(label = "Descripción de la evidencia (opcional)") {
            OutlinedTextField(
                value = formState.description,
                onValueChange = { viewModel.onDescriptionChanged(it) },
                label = { Text(text = "Describe las actividades realizadas o resultados obtenidos.") },
                singleLine = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.uploadAndRegister(context)
            },
            enabled = formState.isSubmissionEnabled,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0B81B7),
                disabledContainerColor = Color(0xFFF3F4F6),
                disabledContentColor = Color(0xFF99A1AF)
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_lucide_paperclip),
                contentDescription = "Registrar evidencia."
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Registrar Evidencia")
        }
    }
}