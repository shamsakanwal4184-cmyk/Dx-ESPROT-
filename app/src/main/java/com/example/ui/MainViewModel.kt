package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.entities.*
import com.example.data.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    // Auth State
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    // Tournament Data
    val tournaments: StateFlow<List<Tournament>> = repository.getAllTournaments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // UI State for specific tournament
    private val _selectedTournament = MutableStateFlow<Tournament?>(null)
    val selectedTournament: StateFlow<Tournament?> = _selectedTournament.asStateFlow()

    // User Data
    private val _userRegistrations = MutableStateFlow<List<Registration>>(emptyList())
    val userRegistrations: StateFlow<List<Registration>> = _userRegistrations.asStateFlow()

    // Admin Data
    val allRegistrations: StateFlow<List<Registration>> = repository.getAllRegistrations()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.initSampleData()
        }

        // Observe user registrations when user logs in
        viewModelScope.launch {
            currentUser.collect { user ->
                if (user != null) {
                    repository.getRegistrationsByUser(user.id).collect {
                        _userRegistrations.value = it
                    }
                } else {
                    _userRegistrations.value = emptyList()
                }
            }
        }
    }

    // Actions
    fun login(emailOrMobile: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val user = repository.login(emailOrMobile, password)
            if (user != null) {
                _currentUser.value = user
                onSuccess()
            } else {
                onError("Invalid credentials")
            }
        }
    }

    fun register(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            // Check if exists
            val existing = repository.login(user.email, user.password) // reuse login check logic loosely
            if (existing == null) {
                val id = repository.register(user)
                _currentUser.value = user.copy(id = id)
                onSuccess()
            } else {
                onError("User already exists")
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _isAdminLoggedIn.value = false
    }

    fun adminLogin(password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (password == "DANIAL098") {
            _isAdminLoggedIn.value = true
            onSuccess()
        } else {
            onError("Incorrect admin password")
        }
    }

    fun selectTournament(tournament: Tournament) {
        _selectedTournament.value = tournament
    }

    fun registerForTournament(registration: Registration, onSuccess: (Long) -> Unit) {
        viewModelScope.launch {
            val regId = repository.registerForTournament(registration)
            onSuccess(regId)
            // Add notification
            repository.addNotification(
                Notification(
                    userId = registration.userId,
                    title = "Registration Successful",
                    message = "You have successfully registered for ${selectedTournament.value?.name}"
                )
            )
        }
    }

    // Admin Actions
    fun createTournament(tournament: Tournament) {
        viewModelScope.launch {
            repository.createTournament(tournament)
            repository.addNotification(
                Notification(
                    userId = null,
                    title = "New Tournament Announced",
                    message = "${tournament.name} is now open for registration!"
                )
            )
        }
    }

    fun updateTournament(tournament: Tournament) {
        viewModelScope.launch {
            repository.updateTournament(tournament)
        }
    }

    fun deleteTournament(tournament: Tournament) {
        viewModelScope.launch {
            repository.deleteTournament(tournament)
        }
    }

    fun updateRegistrationStatus(registration: Registration, status: String) {
        viewModelScope.launch {
            val updated = registration.copy(regStatus = status)
            repository.updateRegistration(updated)
            repository.addNotification(
                Notification(
                    userId = registration.userId,
                    title = "Registration Updated",
                    message = "Your registration for tournament ID ${registration.tournamentId} is now $status"
                )
            )
        }
    }
}
