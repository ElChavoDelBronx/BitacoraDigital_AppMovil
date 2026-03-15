package mx.edu.utez.bitacora.data.network

import mx.edu.utez.bitacora.data.network.responses.AuthResponse
import mx.edu.utez.bitacora.data.network.responses.GeneralResponse
import mx.edu.utez.bitacora.ui.features.recovery.data.PasswordResetRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/auth/request-reset")
    suspend fun sendResetRequest(@Body request: PasswordResetRequest): Response<GeneralResponse<Unit>>

    @POST("api/auth/verify-reset")
    suspend fun verifyReset(@Body request: PasswordResetRequest): Response<GeneralResponse<Unit>>

    @POST("api/auth/change-password")
    suspend fun changePassword(@Body request: PasswordResetRequest): Response<AuthResponse>
}