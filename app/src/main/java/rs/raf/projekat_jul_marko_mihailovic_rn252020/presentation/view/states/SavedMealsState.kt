package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states

import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.SavedMeal

sealed class SavedMealsState {
    object Loading: SavedMealsState()
    object DataFetched: SavedMealsState()
    data class Success(val meals: List<SavedMeal>): SavedMealsState()
    data class Error(val message: String): SavedMealsState()
}
