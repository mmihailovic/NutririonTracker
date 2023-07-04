package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states

import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategory

sealed class MealsCateogoryState {
    object Loading: MealsCateogoryState()
    object DataFetched: MealsCateogoryState()
    data class Success(val movies: List<MealCategory>): MealsCateogoryState()
    data class Error(val message: String): MealsCateogoryState()
}