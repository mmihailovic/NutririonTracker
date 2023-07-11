package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.SavedMeal
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.LayoutItemJeloBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.SavedMealViewModel
import java.io.File
import java.time.Instant
import java.time.ZoneId

class SavedMealViewHolder(
    private val itemBinding: LayoutItemJeloBinding,
    private val viewModel: SavedMealViewModel
) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(meal: SavedMeal) {
        val dateTime = Instant.ofEpochSecond(meal.date)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        val day = dateTime.dayOfMonth
        val month = dateTime.monthValue
        val year = dateTime.year

        itemBinding.nazivJela.text = meal.strMeal
        itemBinding.kategorijaJela.text = meal.strCategory
        itemBinding.obrok.text = meal.meal
        itemBinding.datum.text = String.format("%d.%d.%d",day,month,year)
        itemBinding.link.text = meal.strYoutube
        itemBinding.instrukcije.text = meal.strInstructions
        itemBinding.edit.setOnClickListener {
            viewModel.editMeal.value = meal
        }
        itemBinding.delete.setOnClickListener {
            viewModel.deleteMeal.value = meal
        }
        val stringBuilder = StringBuilder()
        sastojak(stringBuilder, meal.strIngredient1, meal.strMeasure1)
        sastojak(stringBuilder, meal.strIngredient2, meal.strMeasure2)
        sastojak(stringBuilder, meal.strIngredient3, meal.strMeasure3)
        sastojak(stringBuilder, meal.strIngredient4, meal.strMeasure4)
        sastojak(stringBuilder, meal.strIngredient5, meal.strMeasure5)
        sastojak(stringBuilder, meal.strIngredient6, meal.strMeasure6)
        sastojak(stringBuilder, meal.strIngredient7, meal.strMeasure7)
        sastojak(stringBuilder, meal.strIngredient8, meal.strMeasure8)
        sastojak(stringBuilder, meal.strIngredient9, meal.strMeasure9)
        sastojak(stringBuilder, meal.strIngredient10, meal.strMeasure10)
        sastojak(stringBuilder, meal.strIngredient11, meal.strMeasure11)
        sastojak(stringBuilder, meal.strIngredient12, meal.strMeasure12)
        sastojak(stringBuilder, meal.strIngredient13, meal.strMeasure13)
        sastojak(stringBuilder, meal.strIngredient14, meal.strMeasure14)
        sastojak(stringBuilder, meal.strIngredient15, meal.strMeasure15)
        sastojak(stringBuilder, meal.strIngredient16, meal.strMeasure16)
        sastojak(stringBuilder, meal.strIngredient17, meal.strMeasure17)
        sastojak(stringBuilder, meal.strIngredient18, meal.strMeasure18)
        sastojak(stringBuilder, meal.strIngredient19, meal.strMeasure19)
        sastojak(stringBuilder, meal.strIngredient20, meal.strMeasure20)
        stringBuilder.deleteCharAt(stringBuilder.length - 1)
        itemBinding.sastojci.text = stringBuilder.toString()
        if(meal.strMealThumb.startsWith("https"))Picasso.get().load(meal.strMealThumb).into(itemBinding.slikaJela)
        else Picasso.get()
            .load(File(meal.strMealThumb))
            .fit()
            .centerCrop()
            .into(itemBinding.slikaJela)
    }

    private fun sastojak(stringBuilder: StringBuilder, sastojak: String?, mera: String?) {
        if(sastojak != null) {
            stringBuilder.append(sastojak + " " + mera + "\n")
        }
    }
}