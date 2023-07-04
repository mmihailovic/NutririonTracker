package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states

sealed class AddMealState {
    object Success: AddMealState()
    data class Error(val message: String): AddMealState()
}
