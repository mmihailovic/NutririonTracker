package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Meal

class MealDiffCallback : DiffUtil.ItemCallback<Meal>() {

    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.strCategory == newItem.strCategory && oldItem.strArea == newItem.strArea && oldItem.strMeal == newItem.strMeal
    }

}