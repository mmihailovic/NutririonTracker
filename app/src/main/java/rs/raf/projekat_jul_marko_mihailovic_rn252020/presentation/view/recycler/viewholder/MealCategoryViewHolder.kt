package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.viewholder

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategory
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.LayoutItemMealCategoryBinding

class MealCategoryViewHolder(private val itemBinding: LayoutItemMealCategoryBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(movie: MealCategory) {
        itemBinding.titleTv.text = movie.category
        Picasso.get().load(movie.thumbnail).into(itemBinding.imageView)
        itemBinding.imageButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(itemBinding.root.context)
                .setTitle("Opis")
                .setMessage(movie.description)
                .setPositiveButton("OK") { dialog, which ->

                }
                .create()

            alertDialog.show()
        }
    }

}