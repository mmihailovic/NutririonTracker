package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states

import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Meal

sealed class MealsState {
    object Loading: MealsState()
    object DataFetched: MealsState()
    data class Success(val movies: List<Meal>): MealsState()
    data class Error(val message: String): MealsState()
}