package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states

import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.User

sealed class UsersState {
    object Loading: UsersState()
    object DataFetched: UsersState()
    data class Success(val user: User): UsersState()
    data class Error(val message: String): UsersState()
}