package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories

import io.reactivex.Observable
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local.UserDao
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.User
data class UserRepositoryImplementation(
    private val localDataSource: UserDao
) : UserRepository {
    override fun getByUsername(username: String, password: String): Observable<User> {
        return localDataSource
            .getAll(username, password)
            .map {
                User(it.username, it.password)
            }

    }
}
