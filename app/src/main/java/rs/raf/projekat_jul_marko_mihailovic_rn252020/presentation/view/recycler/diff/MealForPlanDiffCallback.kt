package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealForPlan

class MealForPlanDiffCallback : DiffUtil.ItemCallback<MealForPlan>() {

    override fun areItemsTheSame(oldItem: MealForPlan, newItem: MealForPlan): Boolean {
        return oldItem.day == newItem.day && oldItem.meal == newItem.meal && oldItem.mealName == newItem.mealName
    }

    override fun areContentsTheSame(oldItem: MealForPlan, newItem: MealForPlan): Boolean {
        return oldItem.day == newItem.day && oldItem.meal == newItem.meal && oldItem.mealName == newItem.mealName
    }
}