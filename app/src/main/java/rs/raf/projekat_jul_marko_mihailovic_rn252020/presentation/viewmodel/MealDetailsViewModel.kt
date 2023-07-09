package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Resource
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.MealRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.MealDetailsContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsState
import timber.log.Timber

class MealDetailsViewModel(
    private val mealRepository: MealRepository
): ViewModel(), MealDetailsContract.ViewModel {
    override val mealsState: MutableLiveData<MealsState> = MutableLiveData()
    private val subscriptions = CompositeDisposable()

    override fun fetch(id: String) {
        val subscription = mealRepository
            .fetchSingleMeal(id)
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealsState.value = MealsState.Loading
                        is Resource.Success -> mealsState.value = MealsState.DataFetched
                        is Resource.Error -> mealsState.value =
                            MealsState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealsState.value =
                        MealsState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun get(id: String) {

        val subscription = mealRepository
            .find(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("Ucitano jelo " + it.idMeal + " " + it.strMeal)
                    mealsState.value = MealsState.Success(listOf(it))
                },
                {
                    mealsState.value =
                        MealsState.Error("Error happened while fetching data from db")
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