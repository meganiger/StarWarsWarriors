package apps.flotrust.starwarswarriors.ui.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apps.flotrust.starwarswarriors.R
import apps.flotrust.starwarswarriors.domain.model.Warrior
import com.bumptech.glide.Glide

class WarriorAdapter(private var warriors: List<Warrior>) : RecyclerView.Adapter<WarriorAdapter.WarriorViewHolder>() {

    class WarriorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_warrior)
        val textView: TextView = itemView.findViewById(R.id.text_warrior_title)
        val description: TextView = itemView.findViewById(R.id.text_warrior_description)
        fun bind(warrior: Warrior) {
            textView.text = warrior.name
            description.text = warrior.description
            Glide.with(itemView.context)
                .load(warrior.photo) // Загрузка изображения с помощью Glide
                .into(imageView)
        }
    }

    fun updateWarriors(newWarriors: List<Warrior>) {
        warriors = newWarriors
        notifyDataSetChanged() // Уведомляем адаптер, что данные изменились
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarriorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.warrior_item, parent, false)
        return WarriorViewHolder(view)
    }

    override fun onBindViewHolder(holder: WarriorViewHolder, position: Int) {
        holder.bind(warriors[position])
    }

    override fun getItemCount(): Int = warriors.size
}