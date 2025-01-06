package apps.flotrust.starwarswarriors.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apps.flotrust.starwarswarriors.data.network.NetworkClient
import apps.flotrust.starwarswarriors.domain.model.Warrior
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class InfoViewModel : ViewModel() {

    val warriors:MutableStateFlow<List<Warrior>> = MutableStateFlow(listOf())

    init { // при инициализации вью модельки делаем запрос на сервер
        viewModelScope.launch {
            val networkClient = NetworkClient()
            networkClient.getWarriorsInfo()?.let { warriors.emit(it) }
        }

    }

}