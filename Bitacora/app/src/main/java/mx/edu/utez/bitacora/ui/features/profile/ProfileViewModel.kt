package mx.edu.utez.bitacora.ui.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.bitacora.data.local.DataStoreManager
import mx.edu.utez.bitacora.data.network.ApiService
import mx.edu.utez.bitacora.ui.features.profile.data.ProfileInfo

class ProfileViewModel(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private val _uiState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val uiState: StateFlow<ProfileState> = _uiState

    private val _profileInfo = MutableStateFlow(ProfileInfo())
    val profileInfo: StateFlow<ProfileInfo> = _profileInfo

    private val _userName = MutableStateFlow("N/A")
    val userName: StateFlow<String> = _userName

    private val _hourProgress = MutableStateFlow(0f)
    val hourProgress: StateFlow<Float> = _hourProgress

    init {
        observeUserAndLoadDashboard()
    }
    fun observeUserAndLoadDashboard() {
        viewModelScope.launch {
            dataStoreManager.userIdFlow.collect { id ->
                if(id != null) {
                    loadProfile(id)
                }
            }
        }
        viewModelScope.launch {
            dataStoreManager.userNameFlow.collect { userName ->
                if(userName != null) {
                    _userName.value = userName
                }
            }

        }
        viewModelScope.launch {
            profileInfo.collect { info ->
                if(info.neededHours != 0) {
                    _hourProgress.value = info.validatedHours.toFloat() / info.neededHours
                    _uiState.value = ProfileState.Success
                }
            }
        }
    }
    fun loadProfile(userId: Long) {
        viewModelScope.launch {
            try {
                val response = apiService.getStudentProfile(userId)
                if(response.isSuccessful && response.body() != null){
                    _profileInfo.value = response.body()!!.data
                } else {
                    _uiState.value = ProfileState.Error("Error: ${response.code()}}")
                }

            } catch (e: Exception) {
                _uiState.value = ProfileState.Error("Error: ${e.message}}")
            }
        }
    }

}