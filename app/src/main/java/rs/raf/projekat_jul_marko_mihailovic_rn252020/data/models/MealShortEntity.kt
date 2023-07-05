package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="meal_short")
data class MealShortEntity (
    @PrimaryKey
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)