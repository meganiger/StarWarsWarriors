package apps.flotrust.starwarswarriors.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import apps.flotrust.starwarswarriors.domain.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE login = :login AND password = :password")
    suspend fun getUser(login: String, password: String): User?
}