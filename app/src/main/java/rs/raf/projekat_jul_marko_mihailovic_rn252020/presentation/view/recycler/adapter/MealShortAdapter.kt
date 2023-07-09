package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShort
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.LayoutItemMealBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.diff.MealShortDiffCallback
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.viewholder.MealShortViewHolder
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.FilterViewModel
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.MealsForCategoryViewModel

class MealShortAdapter(private val viewmodel: ViewModel?) : ListAdapter<MealShort, MealShortViewHolder>(MealShortDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealShortViewHolder {
        val itemBinding = LayoutItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealShortViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MealShortViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            when(viewmodel) {
                is MealsForCategoryViewModel ->
                    viewmodel!!.clickedItem.value = getItem(position)
                is FilterViewModel ->
                    viewmodel!!.clickedItem.value = getItem(position)

            }
        }
    }

}