package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategory

class MealDiffCallback : DiffUtil.ItemCallback<MealCategory>() {

    override fun areItemsTheSame(oldItem: MealCategory, newItem: MealCategory): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MealCategory, newItem: MealCategory): Boolean {
        return oldItem.category == newItem.category && oldItem.thumbnail == newItem.thumbnail
    }

}