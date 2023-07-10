package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.SavedMeal
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.SavedMealRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.SaveMealContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.AddMealState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.SavedMealsState
import timber.log.Timber

class SavedMealViewModel(
    private val savedMealRepository: SavedMealRepository
): ViewModel(),  SaveMealContract.ViewModel{

    private val subscriptions = CompositeDisposable()
    override val mealsState: MutableLiveData<SavedMealsState> = MutableLiveData()
    override val saveMealState: MutableLiveData<AddMealState> = MutableLiveData()
    override val editMeal: MutableLiveData<SavedMeal> = MutableLiveData()
    override val deleteMeal: MutableLiveData<SavedMeal> = MutableLiveData()

    override fun find(id: String) {
        val subscription = savedMealRepository
            .find(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealsState.value = SavedMealsState.Success(listOf(it))
                },
                {
                    mealsState.value =
                        SavedMealsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAll() {
        val subscription = savedMealRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealsState.value = SavedMealsState.Success(it)
                },
                {
                    mealsState.value =
                        SavedMealsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun insert(meal: SavedMeal) {
        val subscription = savedMealRepository
            .insert(meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    saveMealState.value = AddMealState.Success
                },
                {
                    saveMealState.value =
                        AddMealState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun delete(idMeal: String) {
        val subscription = savedMealRepository
            .delete(idMeal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    saveMealState.value = AddMealState.Success
                },
                {
                    saveMealState.value =
                        AddMealState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun deleteAll() {
        val subscription = savedMealRepository
            .deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    saveMealState.value = AddMealState.Success
                },
                {
                    saveMealState.value =
                        AddMealState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun update(meal: SavedMeal) {
        val subscription = savedMealRepository
            .update(meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    saveMealState.value = AddMealState.Success
                },
                {
                    saveMealState.value =
                        AddMealState.Error("Error happened while fetching data from db")
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