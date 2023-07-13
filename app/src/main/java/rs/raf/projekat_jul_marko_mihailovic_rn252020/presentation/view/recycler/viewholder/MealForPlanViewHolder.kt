package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealForPlan
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.LayoutMealForPlanItemBinding

class MealForPlanViewHolder(private val itemBinding: LayoutMealForPlanItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: MealForPlan) {
        itemBinding.textView6.text = meal.day
        itemBinding.textView7.text = meal.meal
        itemBinding.textView8.text = meal.mealName
    }
}