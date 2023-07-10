package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.SavedMeal
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.LayoutItemJeloBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.diff.SavedMealDiffCallback
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.viewholder.SavedMealViewHolder
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.SavedMealViewModel

class SavedMealAdapter(
    private val viewModel: SavedMealViewModel
) : ListAdapter<SavedMeal, SavedMealViewHolder>(SavedMealDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedMealViewHolder {
        val itemBinding = LayoutItemJeloBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedMealViewHolder(itemBinding, viewModel)
    }

    override fun onBindViewHolder(holder: SavedMealViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}