package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.SavedMeal
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.AddMealState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.SavedMealsState

interface SaveMealContract {
    interface ViewModel {
        val mealsState: LiveData<SavedMealsState>
        val saveMealState: LiveData<AddMealState>
        val editMeal: LiveData<SavedMeal>
        val deleteMeal: LiveData<SavedMeal>
        fun find(id: String)
        fun getAll()
        fun insert(meal: SavedMeal)
        fun delete(idMeal: String)
        fun deleteAll()
        fun update(meal: SavedMeal)

    }
}