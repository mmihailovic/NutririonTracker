package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters()
abstract class UserDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
}