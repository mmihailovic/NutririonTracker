package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealForPlan
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.LayoutMealForPlanItemBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.diff.MealForPlanDiffCallback
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.viewholder.MealForPlanViewHolder

class MealForPlanAdapter : ListAdapter<MealForPlan, MealForPlanViewHolder>(MealForPlanDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealForPlanViewHolder {
        val itemBinding =
            LayoutMealForPlanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealForPlanViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MealForPlanViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}