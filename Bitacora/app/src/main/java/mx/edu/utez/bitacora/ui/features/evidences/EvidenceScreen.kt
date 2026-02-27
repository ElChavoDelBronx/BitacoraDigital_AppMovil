package mx.edu.utez.bitacora.ui.features.evidences

import android.net.Uri
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.bitacora.ui.components.SpinnerDropdown
import mx.edu.utez.bitacora.R
import mx.edu.utez.bitacora.ui.features.evidences.components.AttachedFileItem
import mx.edu.utez.bitacora.ui.components.SelectableButton
import mx.edu.utez.bitacora.ui.features.evidences.components.InputGroup

enum class EvidenceType(@StringRes val labelRestId: Int){
    Text(R.string.evidence_type_text),
    File(R.string.evidence_type_file)
}

@Composable
fun EvidenceScreen() {
    val tasks = listOf(
        "Diseñar base de datos relacional",
        "Desarrollar API REST de inscripciones",
        "Pruebas unitarias del módulo",
        "Encuestas de satisfacción"
    )
    var selectedTask by remember { mutableStateOf("") }
    var workedHours by remember { mutableStateOf("") }
    var textEvidence by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    var selectedInput by remember { mutableStateOf(EvidenceType.File) }
    var selectedFileUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val pickFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris ->
            // Actualiza la lista de uris sin duplicar archivos
            selectedFileUris = (selectedFileUris + uris).distinct()
        }
    )
    val isSubmittingEnabled = (selectedTask.isNotEmpty() && workedHours.isNotEmpty()) && (
                textEvidence.isNotEmpty() && selectedInput == EvidenceType.Text
            ) || (
                selectedFileUris.isNotEmpty() && selectedInput == EvidenceType.File
            )

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
                text = "Texto o archivos adjuntos",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        InputGroup(label = "TAREA ASOCIADA") {
            SpinnerDropdown(
                text = "Selecciona una tarea",
                list = tasks,
                onSelectedItem = { selected ->
                    selectedTask = selected
                }
            )
        }
        InputGroup(label = "horas trabajadas") {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = workedHours,
                onValueChange = { workedHours = it },
                label = { Text(text = "")  }
            )
        }
        InputGroup(label = "TIPO DE EVIDENCIA") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ){
                SelectableButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = EvidenceType.Text.labelRestId),
                    option = EvidenceType.Text,
                    selectedOption = selectedInput,
                    onClick = { selectedInput = it },
                    icon = painterResource(id = R.drawable.ic_lucide_file_text)
                )
                SelectableButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = EvidenceType.File.labelRestId),
                    option = EvidenceType.File,
                    selectedOption = selectedInput,
                    onClick = { selectedInput = it },
                    icon = painterResource(id = R.drawable.ic_lucide_upload)
                )
            }
        }
        // Alterna entre el input de texto e input de archivos
        when(selectedInput) {
            EvidenceType.Text -> {
                InputGroup(label = "Descripción de la evidencia") {
                    OutlinedTextField(
                        value = textEvidence,
                        onValueChange = { textEvidence = it },
                        label = { Text(text = "Describe las actividades realizadas o resultados obtenidos.") },
                        singleLine = false,
                        modifier = Modifier.fillMaxWidth()
                            .height(272.dp)
                    )
                }
            }
            EvidenceType.File -> {
                InputGroup(label = "Archivos adjuntos") {
                    // Alterna entre botón para adjuntar el primer archivo y botón para agregar más archivos
                    if(selectedFileUris.isEmpty()){
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
                            selectedFileUris.forEach { uri ->
                              AttachedFileItem(
                                  fileUri = uri,
                                  onRemoveClick = {
                                      selectedFileUris = selectedFileUris - uri
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
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { },
            enabled = isSubmittingEnabled,
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
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun EvidencePreview(){
    EvidenceScreen()
}