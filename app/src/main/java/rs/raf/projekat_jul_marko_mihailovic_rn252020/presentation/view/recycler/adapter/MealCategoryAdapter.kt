package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategory
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.LayoutItemMealCategoryBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.diff.MealCategoryDiffCallback
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.viewholder.MealCategoryViewHolder

class MealCategoryAdapter : ListAdapter<MealCategory, MealCategoryViewHolder>(MealCategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealCategoryViewHolder {
        val itemBinding = LayoutItemMealCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealCategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MealCategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}