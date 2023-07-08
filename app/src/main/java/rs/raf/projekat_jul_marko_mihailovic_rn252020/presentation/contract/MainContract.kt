package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.AddMealState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsCateogoryState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategory
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsState

interface MainContract {
    interface ViewModel {
        val mealsCategoryState: LiveData<MealsCateogoryState>
        val mealsState: LiveData<MealsState>
        val addDone: LiveData<AddMealState>
        val clickedItem: LiveData<MealCategory>

        fun fetchAllMovies()
        fun getAllMovies()
        fun getMoviesByName(name: String)
        fun addMeal(movie: MealCategory)
        fun fetchAllMeals(name: String)
        fun getAllMeals()
    }
}