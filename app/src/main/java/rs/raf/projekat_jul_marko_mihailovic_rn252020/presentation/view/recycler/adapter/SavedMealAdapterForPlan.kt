package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.SavedMeal
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.LayoutSavedMealItemForPlanBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.diff.SavedMealForPlanDiffCallback
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.viewholder.SavedMealForPlanViewHolder
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.FormViewModel

class SavedMealAdapterForPlan(
    private val viewModel: FormViewModel
) : ListAdapter<SavedMeal, SavedMealForPlanViewHolder>(SavedMealForPlanDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedMealForPlanViewHolder {
        val itemBinding = LayoutSavedMealItemForPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedMealForPlanViewHolder(itemBinding,viewModel)
    }

    override fun onBindViewHolder(holder: SavedMealForPlanViewHolder, position: Int) {
        holder.bind(getItem(position))
        val isSelected = viewModel.selectedFromDatabaseIndex.value == position
        if (isSelected) {
            holder.itemView.setBackgroundColor(Color.CYAN)
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}