package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states

import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShort

sealed class MealsShortState {
    object Loading: MealsShortState()
    object DataFetched: MealsShortState()
    data class Success(val movies: List<MealShort>): MealsShortState()
    data class Error(val message: String): MealsShortState()
}