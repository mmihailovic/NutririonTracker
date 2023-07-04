package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategoryEntity

@Database(
    entities = [MealCategoryEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters()
abstract class MealCategoryDatabase: RoomDatabase() {
    abstract fun getMealDao(): MealDao
}