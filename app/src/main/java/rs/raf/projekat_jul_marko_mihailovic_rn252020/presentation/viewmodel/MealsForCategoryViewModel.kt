package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShort
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Resource
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.MealShortRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.MealsForCategoryContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsShortState
import timber.log.Timber

class MealsForCategoryViewModel(
    private val mealRepository: MealShortRepository
): ViewModel(), MealsForCategoryContract.ViewModel {
    private val subscriptions = CompositeDisposable()
    override val mealsShortState: MutableLiveData<MealsShortState> = MutableLiveData()
    override val page: MutableLiveData<Int> = MutableLiveData()
    override val clickedItem: MutableLiveData<MealShort> = MutableLiveData()

    override fun fetchAllMeals(name: String) {
        val subscription = mealRepository
            .fetchAllByCategory(name)
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealsShortState.value = MealsShortState.Loading
                        is Resource.Success -> mealsShortState.value = MealsShortState.DataFetched
                        is Resource.Error -> mealsShortState.value =
                            MealsShortState.Error("Error happened while fetching meals data from the server")
                    }
                },
                {
                    mealsShortState.value =
                        MealsShortState.Error("Error happened while fetching meals data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllMeals() {
        val subscription = mealRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealsShortState.value = MealsShortState.Success(it)
                },
                {
                    mealsShortState.value =
                        MealsShortState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllMealsWithPagination(pocetak: Int) {
        val subscription = mealRepository
            .getAllWithPagination(pocetak)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealsShortState.value = MealsShortState.Success(it)
                },
                {
                    mealsShortState.value =
                        MealsShortState.Error("Error happened while fetching data from db")
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