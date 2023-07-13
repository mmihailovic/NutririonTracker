package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealForPlan
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShort
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.SavedMeal
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.SavedMealRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.FormContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.SavedMealsState
import timber.log.Timber

class FormViewModel(
    private val savedMealRepository: SavedMealRepository,
): ViewModel(), FormContract.ViewModel {
    private val subscriptions = CompositeDisposable()
    override val savedMealsState: MutableLiveData<SavedMealsState> = MutableLiveData()
    override val mealsState: MutableLiveData<MealsState> = MutableLiveData()
    override val meals: MutableLiveData<List<Pair<String, List<MealForPlan>>>> = MutableLiveData()
    override val selectedFromDatabase: MutableLiveData<SavedMeal> = MutableLiveData()
    override val selectedFromDatabaseIndex: MutableLiveData<Int> = MutableLiveData()
    override val selectedFromApi: MutableLiveData<MealShort> = MutableLiveData()
    override val selectedFromApiIndex: MutableLiveData<Int> = MutableLiveData()

    override fun addMeal(day: String, obrok: String) {
        val currentList = meals.value?.toMutableList() ?: mutableListOf()

        val existingPairIndex = currentList.indexOfFirst { it.first == day }
        if (existingPairIndex != -1) {
            val existingPair = currentList[existingPairIndex]
            val existingMeals = existingPair.second.toMutableList()
            existingMeals.add(MealForPlan(day,selectedFromDatabase.value!!.strMeal,obrok))
            currentList[existingPairIndex] = existingPair.copy(second = existingMeals)
        }
        else {
            val newPair = Pair(day, listOf(MealForPlan(day,selectedFromDatabase.value!!.strMeal,obrok)))
            currentList.add(newPair)
        }

        meals.value = currentList
    }

    override fun addMealFromApi(day: String, obrok: String) {
        val currentList = meals.value?.toMutableList() ?: mutableListOf()

        val existingPairIndex = currentList.indexOfFirst { it.first == day }
        if (existingPairIndex != -1) {
            val existingPair = currentList[existingPairIndex]
            val existingMeals = existingPair.second.toMutableList()
            existingMeals.add(MealForPlan(day,selectedFromApi.value!!.strMeal,obrok))
            currentList[existingPairIndex] = existingPair.copy(second = existingMeals)
        }
        else {
            val newPair = Pair(day, listOf(MealForPlan(day,selectedFromApi.value!!.strMeal,obrok)))
            currentList.add(newPair)
        }

        meals.value = currentList
    }

    override fun getAllFromDatabase() {
        val subscription = savedMealRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    savedMealsState.value = SavedMealsState.Success(it)
                },
                {
                    savedMealsState.value =
                        SavedMealsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }
}