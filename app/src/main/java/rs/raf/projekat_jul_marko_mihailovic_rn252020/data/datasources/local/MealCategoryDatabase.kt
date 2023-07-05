package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategoryEntity
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealEntity
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShortEntity

@Database(
    entities = [MealCategoryEntity::class, MealEntity::class, MealShortEntity::class],
    version = 5,
    exportSchema = false)
@TypeConverters()
abstract class MealCategoryDatabase: RoomDatabase() {
    abstract fun getMealCategoryDao(): MealCategoryDao
    abstract fun getMealDao(): MealDao
    abstract fun getMealShortDao(): MealShortDao
}