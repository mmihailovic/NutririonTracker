package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategory
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.LayoutItemMealBinding

class MealViewHolder(private val itemBinding: LayoutItemMealBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(movie: MealCategory) {
        itemBinding.titleTv.text = movie.category
        Picasso.get().load(movie.thumbnail).into(itemBinding.imageView)
    }

}