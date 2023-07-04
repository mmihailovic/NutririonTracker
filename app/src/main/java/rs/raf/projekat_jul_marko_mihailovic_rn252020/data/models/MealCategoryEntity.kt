package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class MealCategoryEntity (
    @PrimaryKey
    val id:String,
    val category: String,
    val thumbnail: String,
    val description: String
)
