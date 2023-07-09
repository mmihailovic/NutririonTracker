package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Resource
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.MealShortRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.IngredientContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsShortState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class IngredientViewModel(
    private val mealRepository: MealShortRepository
): ViewModel(), IngredientContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val mealsShortState: MutableLiveData<MealsShortState> = MutableLiveData()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                mealRepository
                    .getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
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

    override fun getMealsByIngredient(name: String) {
        publishSubject.onNext(name)
    }

    override fun fetchAllMeals(name: String) {
        val subscription = mealRepository
            .fetchAllByIngredient(name)
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

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}