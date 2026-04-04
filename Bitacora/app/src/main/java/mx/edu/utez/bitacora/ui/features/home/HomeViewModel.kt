package mx.edu.utez.bitacora.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.bitacora.data.local.DataStoreManager
import mx.edu.utez.bitacora.data.network.ApiService
import mx.edu.utez.bitacora.ui.features.home.data.StudentDashboard

class HomeViewModel(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private val _dashboardInfo = MutableStateFlow(StudentDashboard())
    val dashboardInfo: StateFlow<StudentDashboard> = _dashboardInfo

    private val _userName = MutableStateFlow("N/A")
    val userName: StateFlow<String> = _userName

    init {
        observeUserAndLoadDashboard()
    }
    fun observeUserAndLoadDashboard() {
        viewModelScope.launch {
            dataStoreManager.userIdFlow.collect { id ->
                if(id != null) {
                    loadDashboard(id)
                }
            }
        }
        viewModelScope.launch {
            dataStoreManager.userNameFlow.collect { userName ->
                if(userName != null) {
                    _userName.value = userName.substringBefore(" ")
                }
            }
        }
    }
    fun loadDashboard(userId: Long) {
        viewModelScope.launch {
            try {
                val response = apiService.getDashboardInfo(userId)
                if(response.isSuccessful && response.body() != null){
                    _dashboardInfo.value = response.body()!!.data
                }
            } catch (e: Exception) {

            } finally {
            }
        }
    }
}