package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShort
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsShortState

interface FilterContract {
    interface ViewModel {
        val mealsShortState: LiveData<MealsShortState>
        val clickedItem: LiveData<MealShort>

        fun filterByCategory(category: String)
        fun filterByArea(area: String)
        fun filterByIngredient(ingredient: String)
        fun getAll()
        fun getAllWithPagination(pocetak: Int)
        fun fetchAllByCategory(category: String)
        fun fetchAllByArea(area: String)
        fun fetchAllByIngredient(ingredient: String)
        fun filterByName(name: String)
    }
}