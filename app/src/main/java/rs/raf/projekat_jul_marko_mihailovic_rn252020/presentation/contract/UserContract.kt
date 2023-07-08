package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.UsersState

interface UserContract {
    interface ViewModel {
        val usersState: LiveData<UsersState>

        fun get(username: String, password: String)
    }
}