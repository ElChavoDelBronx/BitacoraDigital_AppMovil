package mx.edu.utez.bitacora.data.network

import mx.edu.utez.bitacora.data.model.SimpleTask
import mx.edu.utez.bitacora.data.model.Task
import mx.edu.utez.bitacora.data.network.responses.AuthResponse
import mx.edu.utez.bitacora.data.network.responses.DataResponse
import mx.edu.utez.bitacora.data.network.responses.GeneralResponse
import mx.edu.utez.bitacora.ui.features.evidences.data.EvidenceRequest
import mx.edu.utez.bitacora.ui.features.home.data.StudentDashboard
import mx.edu.utez.bitacora.ui.features.profile.data.ProfileInfo
import mx.edu.utez.bitacora.ui.features.recovery.data.PasswordResetRequest
import mx.edu.utez.bitacora.ui.features.taskDetails.requests.UpdateTaskRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/auth/request-reset")
    suspend fun sendResetRequest(@Body request: PasswordResetRequest): Response<GeneralResponse<Unit>>

    @POST("api/auth/verify-reset")
    suspend fun verifyReset(@Body request: PasswordResetRequest): Response<GeneralResponse<Unit>>

    @POST("api/auth/change-password")
    suspend fun changePassword(@Body request: PasswordResetRequest): Response<AuthResponse>

    @GET("api/dashboards/student/{studentId}")
    suspend fun getDashboardInfo(@Path("studentId") idStudent: Long): Response<DataResponse<StudentDashboard>>

    @GET("api/tasks/student/{studentId}")
    suspend fun getTasks(@Path("studentId") idStudent: Long): Response<DataResponse<List<Task>>>

    @GET("api/tasks/in-progress/{studentId}")
    suspend fun getInProgressTasks(@Path("studentId") idStudent: Long): Response<DataResponse<List<SimpleTask>>>

    @GET("api/tasks/{taskId}")
    suspend fun getTaskById(@Path("taskId") taskId: Long): Response<DataResponse<Task>>

    @PUT("api/tasks")
    suspend fun updateTask(@Body request: UpdateTaskRequest): Response<GeneralResponse<Task>>

    @POST("api/evidences")
    suspend fun saveEvidence(@Body request: EvidenceRequest): Response<GeneralResponse<Unit>>

    @GET("api/profile/student/{studentId}")
    suspend fun getStudentProfile(@Path("studentId") idStudent: Long): Response<DataResponse<ProfileInfo>>
}