package mx.edu.utez.bitacora.data.network.responses

data class GeneralResponse<T> (
    val message: String,
    val data: T?
)

data class DataResponse<T> (
    val message: String,
    val data: T
)