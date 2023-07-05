package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsShortState

interface IngredientContract {
    interface ViewModel {
        val mealsShortState: LiveData<MealsShortState>

        fun getMealsByIngredient(name: String)
        fun fetchAllMeals(name: String)
        fun getAllMeals()
    }
}