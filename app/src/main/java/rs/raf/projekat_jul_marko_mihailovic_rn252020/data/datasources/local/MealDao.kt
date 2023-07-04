package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealEntity

@Dao
abstract class MealDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: MealEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<MealEntity>): Completable

    @Query("SELECT * FROM meals")
    abstract fun getAll(): Observable<List<MealEntity>>

    @Query("DELETE FROM meals")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<MealEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
}