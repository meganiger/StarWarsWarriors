package apps.flotrust.starwarswarriors.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Warrior(
    val name: String,
    val photo: String,
    val description: String,
    val side:String
)