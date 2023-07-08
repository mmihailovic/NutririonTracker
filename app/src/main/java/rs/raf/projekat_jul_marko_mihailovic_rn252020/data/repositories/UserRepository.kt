package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories

import io.reactivex.Observable
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.User

interface UserRepository {
    fun getByUsername(username: String, password: String): Observable<User>
}