package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShort
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Resource
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.MealShortRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.FilterContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsShortState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class FilterViewModel(
    private val mealShortRepository: MealShortRepository,
): ViewModel(), FilterContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val mealsShortState: MutableLiveData<MealsShortState> = MutableLiveData()
    override val clickedItem: MutableLiveData<MealShort> = MutableLiveData()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()
    private val publishSubjectArea: PublishSubject<String> = PublishSubject.create()
    private val publishSubjectIngredient: PublishSubject<String> = PublishSubject.create()
    private val publishSubjectName: PublishSubject<String> = PublishSubject.create()
    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                    fetchAllByCategory(it)
                    mealShortRepository.getAllWithPagination(0)
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

        val subscriptionArea = publishSubjectArea
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                fetchAllByArea(it)
                mealShortRepository
                    .getAllWithPagination(0)
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
        subscriptions.add(subscriptionArea)

        val subscriptionIngredient = publishSubjectIngredient
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                fetchAllByIngredient(it)
                mealShortRepository
                    .getAllWithPagination(0)
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
        subscriptions.add(subscriptionIngredient)

        val subscriptionName = publishSubjectName
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                mealShortRepository
                    .getAllWithNameWithPagination(it,0)
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
        subscriptions.add(subscriptionName)
    }

    override fun filterByCategory(category: String) {
        publishSubject.onNext(category)
    }

    override fun filterByArea(area: String) {
        publishSubjectArea.onNext(area)
    }

    override fun filterByIngredient(ingredient: String) {
        publishSubjectIngredient.onNext(ingredient)
    }

    override fun getAll() {
        val subscription = mealShortRepository
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

    override fun getAllWithPagination(pocetak: Int) {
        val subscription = mealShortRepository
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

    override fun fetchAllByCategory(category: String) {
        val subscription = mealShortRepository
            .fetchAllByCategory(category)
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

    override fun fetchAllByArea(area: String) {
        val subscription = mealShortRepository
            .fetchAllByArea(area)
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

    override fun fetchAllByIngredient(ingredient: String) {
        val subscription = mealShortRepository
            .fetchAllByIngredient(ingredient)
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

    override fun filterByName(name: String) {
        publishSubjectName.onNext(name)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}