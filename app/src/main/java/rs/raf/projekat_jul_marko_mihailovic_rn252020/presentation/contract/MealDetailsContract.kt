package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsState

interface MealDetailsContract {
    interface ViewModel {
        val mealsState: LiveData<MealsState>

        fun fetch(id: String)
        fun get(id: String)
    }
}