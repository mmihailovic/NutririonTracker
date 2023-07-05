package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShortEntity

@Dao
abstract class MealShortDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: MealShortEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<MealShortEntity>): Completable

    @Query("SELECT * FROM meal_short")
    abstract fun getAll(): Observable<List<MealShortEntity>>

    @Query("DELETE FROM meal_short")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<MealShortEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
}