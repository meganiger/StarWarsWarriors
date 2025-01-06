package apps.flotrust.starwarswarriors.domain.usecase

import apps.flotrust.starwarswarriors.data.local.UserDao
import apps.flotrust.starwarswarriors.domain.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(login: String, password: String) {
        val user = User(login = login, password = password)
        userDao.insertUser(user)
    }

    suspend fun loginUser(login: String, password: String): User? {
        return userDao.getUser(login, password)
    }
}