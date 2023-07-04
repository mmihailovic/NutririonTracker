package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.AddMealState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategory

interface MainContract {
    interface ViewModel {
        val mealsState: LiveData<MealsState>
        val addDone: LiveData<AddMealState>

        fun fetchAllMovies()
        fun getAllMovies()
        fun getMoviesByName(name: String)
        fun addMeal(movie: MealCategory)
    }
}