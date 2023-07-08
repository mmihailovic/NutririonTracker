package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShort
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsShortState

interface MealsForCategoryContract {
    interface ViewModel {
        val mealsShortState: LiveData<MealsShortState>
        val clickedItem: LiveData<MealShort>
        val page: LiveData<Int>

        fun fetchAllMeals(name: String)
        fun getAllMeals()
        fun getAllMealsWithPagination(pocetak: Int)
    }
}