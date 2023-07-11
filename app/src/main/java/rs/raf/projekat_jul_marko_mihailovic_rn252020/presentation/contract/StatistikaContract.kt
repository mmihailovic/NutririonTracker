package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.CountMealsState

interface StatistikaContract {
    interface ViewModel {
        val mealsState: LiveData<CountMealsState>

        fun count(date: List<Long>)
    }
}