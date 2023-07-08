package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.UserRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.UserContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.UsersState
import timber.log.Timber

class UserViewModel(
    private val userRepository: UserRepository
): ViewModel(), UserContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val usersState: MutableLiveData<UsersState> = MutableLiveData()

    override fun get(username: String, password: String) {
        val subscription = userRepository
            .getByUsername(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    usersState.value = UsersState.Success(it)
                },
                {
                    usersState.value =
                        UsersState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}