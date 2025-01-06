package apps.flotrust.starwarswarriors.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apps.flotrust.starwarswarriors.domain.usecase.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginStatus = MutableStateFlow("")
    val loginStatus: MutableStateFlow<String> = _loginStatus

    fun registerUser(login: String, password: String) {
        viewModelScope.launch {
            repository.registerUser(login, password)
            _loginStatus.emit("Пользователь зарегистрирован")
        }
    }

    fun loginUser(login: String, password: String) {
        viewModelScope.launch {
            val user = repository.loginUser(login, password)
            if (user != null) {
                _loginStatus.emit("Вход выполнен: ${user.login}")
            } else {
                _loginStatus.emit("Неверный логин или пароль")
            }
        }
    }
}