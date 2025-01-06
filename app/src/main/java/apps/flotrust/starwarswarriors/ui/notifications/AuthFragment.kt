package apps.flotrust.starwarswarriors.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import apps.flotrust.starwarswarriors.R
import apps.flotrust.starwarswarriors.data.local.AppDatabase
import apps.flotrust.starwarswarriors.databinding.FragmentAuthBinding
import apps.flotrust.starwarswarriors.domain.usecase.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NotificationsFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel:AuthViewModel
    private var isRegistrationMode = false // Флаг для определения текущего режима

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val database = AppDatabase.getDatabase(requireActivity()) // Получаем экземпляр базы данных
        val userDao = database.userDao() // Получаем DAO
        val userRepository = UserRepository(userDao) // Передаем DAO в репозиторий

        viewModel =  NotificationsViewModelFactory(userRepository).create(AuthViewModel::class.java) // инициализируем вью модельку

        setupListeners() // устанавливаем слушатели кнопок

         return root
    }

    private fun setupListeners() {

        lifecycleScope.launch {
            viewModel.loginStatus.collect{  // в фоне собираем информацию об статусе авторизации и реагируем на это
                binding.loginStatus.text = it
                if(it == "Пользователь зарегистрирован" || it.contains("Вход выполнен")){
                    binding.buttonRegister.visibility = View.GONE
                    binding.buttonGoToLogin.visibility = View.GONE
                    binding.buttonGoToRegister.visibility = View.GONE
                    binding.buttonLogin.visibility = View.GONE  // убираем все вьюхи
                    delay(400)
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.navigation_dashboard, true) // Удаляет текущий экран из back stack
                        .build()
                    findNavController().navigate(R.id.navigation_home, null, navOptions) // навигируемся на экран home
                }
            }
        }


        // Кнопка "Войти"
        binding.buttonLogin.setOnClickListener { // устанавливаем слушатель на кнопку, блок выполнится по нажатию
            val login = binding.editTextLogin.text.toString()
            val password = binding.editTextPassword.text.toString()
            viewModel.loginUser(login,password) // передаем данные для входа
        }

        // Кнопка "Зарегистрироваться"
        binding.buttonRegister.setOnClickListener { // устанавливаем слушатель на кнопку, блок выполнится по нажатию
            val login = binding.editTextLogin.text.toString()
            val password = binding.editTextPassword.text.toString()
            viewModel.registerUser(login,password)// передаем данные для регистрации
        }

        // Кнопка "Отмена" для возврата к экрану входа
        binding.buttonCancel.setOnClickListener {
            switchToLoginMode()
        }

        // Кнопка для перехода к регистрации
        binding.buttonGoToRegister.setOnClickListener {
            switchToRegistrationMode()
        }

        // Кнопка для возврата к входу
        binding.buttonGoToLogin.setOnClickListener {
            switchToLoginMode()
        }
    }

    private fun switchToRegistrationMode() {
        isRegistrationMode = true

        binding.buttonLogin.visibility = View.GONE
        binding.buttonGoToRegister.visibility = View.GONE

        binding.buttonRegister.visibility = View.VISIBLE
        binding.buttonGoToLogin.visibility = View.VISIBLE
    }

    private fun switchToLoginMode() {
        isRegistrationMode = false

        binding.buttonLogin.visibility = View.VISIBLE
        binding.buttonGoToRegister.visibility = View.VISIBLE

        binding.buttonRegister.visibility = View.GONE
        binding.buttonGoToLogin.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



class NotificationsViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}