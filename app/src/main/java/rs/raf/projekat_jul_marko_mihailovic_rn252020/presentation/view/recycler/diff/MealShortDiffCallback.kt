package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShort

class MealShortDiffCallback : DiffUtil.ItemCallback<MealShort>() {

    override fun areItemsTheSame(oldItem: MealShort, newItem: MealShort): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: MealShort, newItem: MealShort): Boolean {
        return oldItem.strMeal == newItem.strMeal
    }
}