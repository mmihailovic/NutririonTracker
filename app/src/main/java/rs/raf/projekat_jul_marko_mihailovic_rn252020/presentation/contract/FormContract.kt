package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealForPlan
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShort
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.SavedMeal
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.SavedMealsState

interface FormContract {
    interface ViewModel {
        val savedMealsState: LiveData<SavedMealsState>
        val mealsState: LiveData<MealsState>
        val meals: LiveData<List<Pair<String,List<MealForPlan>>>>
        val selectedFromDatabase: LiveData<SavedMeal>
        val selectedFromDatabaseIndex: LiveData<Int>
        val selectedFromApi: LiveData<MealShort>
        val selectedFromApiIndex: LiveData<Int>

        fun addMeal(day: String, obrok: String)
        fun addMealFromApi(day: String, obrok: String)
        fun getAllFromDatabase()
    }
}