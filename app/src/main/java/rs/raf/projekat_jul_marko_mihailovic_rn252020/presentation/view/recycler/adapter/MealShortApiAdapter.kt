package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShort
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.LayoutMealApiItemBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.diff.MealShortDiffCallback
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.viewholder.MealApiViewHolder
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.FormViewModel

class MealShortApiAdapter (private val viewModel: FormViewModel) : ListAdapter<MealShort, MealApiViewHolder>(
    MealShortDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealApiViewHolder {
        val itemBinding = LayoutMealApiItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealApiViewHolder(itemBinding, viewModel)
    }

    override fun onBindViewHolder(holder: MealApiViewHolder, position: Int) {
        holder.bind(getItem(position))
        val isSelected = viewModel.selectedFromApiIndex.value == position
        if (isSelected) {
            holder.itemView.setBackgroundColor(Color.CYAN)
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}