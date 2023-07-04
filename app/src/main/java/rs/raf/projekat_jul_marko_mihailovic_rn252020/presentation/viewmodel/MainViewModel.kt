package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.AddMealState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsCateogoryState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategory
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Resource
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.MealCategoryRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.MainContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.MealRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val mealCategoryRepository: MealCategoryRepository,
    private val mealRepository:MealRepository
): ViewModel(), MainContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val mealsCategoryState: MutableLiveData<MealsCateogoryState> = MutableLiveData()
    override val mealsState: MutableLiveData<MealsState> = MutableLiveData()
    override val addDone: MutableLiveData<AddMealState> = MutableLiveData()

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
                    mealsState.value = MealsState.Success(it)
                },
                {
                    mealsState.value =
                        MealsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllMovies() {
        val subscription = mealCategoryRepository
            .fetchAll()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealsCategoryState.value = MealsCateogoryState.Loading
                        is Resource.Success -> mealsCategoryState.value = MealsCateogoryState.DataFetched
                        is Resource.Error -> mealsCategoryState.value =
                            MealsCateogoryState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealsCategoryState.value =
                        MealsCateogoryState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllMovies() {
        val subscription = mealCategoryRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealsCategoryState.value = MealsCateogoryState.Success(it)
                },
                {
                    mealsCategoryState.value =
                        MealsCateogoryState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMoviesByName(name: String) {
        publishSubject.onNext(name)
    }

    override fun addMeal(movie: MealCategory) {
        val subscription = mealCategoryRepository
            .insert(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addDone.value = AddMealState.Success
                },
                {
                    addDone.value = AddMealState.Error("Error happened while adding movie")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllMeals(name: String) {
        val subscription = mealRepository
            .fetchAll(name)
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealsState.value = MealsState.Loading
                        is Resource.Success -> mealsState.value = MealsState.DataFetched
                        is Resource.Error -> mealsState.value =
                            MealsState.Error("Error happened while fetching meals data from the server")
                    }
                },
                {
                    mealsState.value =
                        MealsState.Error("Error happened while fetching meals data from the server")
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
                    mealsState.value = MealsState.Success(it)
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