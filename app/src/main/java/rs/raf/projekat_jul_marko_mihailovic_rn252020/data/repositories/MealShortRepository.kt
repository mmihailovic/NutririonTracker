package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShort
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Resource

interface MealShortRepository {
    fun fetchAllByIngredient(name: String): Observable<Resource<Unit>>
    fun fetchAllByCategory(name: String): Observable<Resource<Unit>>
    fun getAll(): Observable<List<MealShort>>
    fun getAllWithPagination(pocetak: Int): Observable<List<MealShort>>
    fun insert(meal: MealShort): Completable
}