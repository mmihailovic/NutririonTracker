package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.UserEntity

@Dao
abstract class UserDao {
    @Insert( onConflict = OnConflictStrategy.ABORT )
    abstract fun insert(entity: UserEntity): Completable

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    abstract fun getAll(username: String, password: String): Observable<UserEntity>
}