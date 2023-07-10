package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.SavedMealEntity

@Dao
abstract class SavedMealDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: SavedMealEntity): Completable

    @Query("SELECT * FROM saved_meals")
    abstract fun getAll(): Observable<List<SavedMealEntity>>

    @Query("SELECT * FROM saved_meals where idMeal = :id")
    abstract fun find(id: String): Observable<SavedMealEntity>

    @Query("DELETE FROM saved_meals")
    abstract fun deleteAll(): Completable

    @Query("DELETE FROM saved_meals WHERE idMeal = :idMeal")
    abstract fun delete(idMeal: String): Completable

    @Update
    abstract fun update(meal: SavedMealEntity): Completable
}