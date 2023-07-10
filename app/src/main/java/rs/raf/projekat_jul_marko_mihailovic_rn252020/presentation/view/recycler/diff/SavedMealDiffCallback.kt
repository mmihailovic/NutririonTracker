package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.SavedMeal

class SavedMealDiffCallback : DiffUtil.ItemCallback<SavedMeal>() {

    override fun areItemsTheSame(oldItem: SavedMeal, newItem: SavedMeal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: SavedMeal, newItem: SavedMeal): Boolean {
        return oldItem.strCategory == newItem.strCategory && oldItem.meal == newItem.meal && oldItem.strMeal == newItem.strMeal && oldItem.date == newItem.date
    }
}