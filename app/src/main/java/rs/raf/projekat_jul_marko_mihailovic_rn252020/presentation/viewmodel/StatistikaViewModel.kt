package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.SavedMealRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.StatistikaContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.CountMealsState
import timber.log.Timber

class StatistikaViewModel(
    private val repository: SavedMealRepository
): ViewModel(), StatistikaContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val mealsState: MutableLiveData<CountMealsState> = MutableLiveData()

    override fun count(date: List<Long>) {
        val subscription = repository
            .count(date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealsState.value = CountMealsState.Success(it)
                },
                {
                    mealsState.value =
                        CountMealsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }
}