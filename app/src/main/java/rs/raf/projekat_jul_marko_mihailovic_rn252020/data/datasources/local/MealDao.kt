package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategoryEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class MealDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: MealCategoryEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<MealCategoryEntity>): Completable

    @Query("SELECT * FROM categories")
    abstract fun getAll(): Observable<List<MealCategoryEntity>>

    @Query("DELETE FROM categories")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<MealCategoryEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

}