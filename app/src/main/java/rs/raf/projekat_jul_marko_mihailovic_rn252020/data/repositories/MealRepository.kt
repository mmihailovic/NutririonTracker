package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Meal
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Resource

interface MealRepository {
    fun fetchAll(name: String): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Meal>>
    fun find(id: String): Observable<Meal>
    fun insert(meal: Meal): Completable
    fun fetchSingleMeal(id: String): Observable<Resource<Unit>>
}