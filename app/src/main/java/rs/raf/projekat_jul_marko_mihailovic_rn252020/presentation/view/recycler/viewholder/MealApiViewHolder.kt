package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShort
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.LayoutMealApiItemBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.FormViewModel

class MealApiViewHolder(
    private val itemBinding: LayoutMealApiItemBinding,
    private val viewModel: FormViewModel
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: MealShort) {
        itemBinding.textViewNaziv.text = meal.strMeal
        Picasso.get().load(meal.strMealThumb).into(itemBinding.imageView2)
        itemBinding.button3.setOnClickListener {
            if(viewModel.selectedFromApiIndex.value != null) bindingAdapter!!.notifyItemChanged(viewModel.selectedFromApiIndex.value as Int)
            viewModel.selectedFromApiIndex.value = bindingAdapterPosition
            viewModel.selectedFromApi.value = meal
            bindingAdapter!!.notifyItemChanged(bindingAdapterPosition)
        }
    }
}