package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states

import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.CountResult

sealed class CountMealsState {
    object Loading: CountMealsState()
    object DataFetched: CountMealsState()
    data class Success(val count: List<CountResult>): CountMealsState()
    data class Error(val message: String): CountMealsState()
}