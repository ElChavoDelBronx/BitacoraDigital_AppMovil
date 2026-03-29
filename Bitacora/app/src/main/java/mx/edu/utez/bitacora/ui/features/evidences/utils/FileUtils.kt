package mx.edu.utez.bitacora.ui.features.evidences.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

fun getFileName(uri: Uri, context: Context): String {
    var name = "archivo_desconocido"
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) name = it.getString(nameIndex)
        }
    }
    return name
}