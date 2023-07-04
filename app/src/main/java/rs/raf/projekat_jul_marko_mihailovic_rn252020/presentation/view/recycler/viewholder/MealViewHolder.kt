package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Meal
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.LayoutItemMealBinding

class MealViewHolder(private val itemBinding: LayoutItemMealBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(movie: Meal) {
        itemBinding.textViewKategorija.text = movie.strCategory
        itemBinding.textViewNaziv.text = movie.strMeal
        itemBinding.textViewOblast.text = movie.strArea
        Picasso.get().load(movie.strMealThumb).into(itemBinding.imageView2)
    }

}