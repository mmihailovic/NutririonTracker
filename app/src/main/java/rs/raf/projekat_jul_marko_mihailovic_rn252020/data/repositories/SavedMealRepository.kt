package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.CountResult
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.SavedMeal

interface SavedMealRepository {
    fun getAll(): Observable<List<SavedMeal>>
    fun find(id: String): Observable<SavedMeal>
    fun insert(meal: SavedMeal): Completable
    fun delete(idMeal: String): Completable
    fun deleteAll(): Completable
    fun update(meal: SavedMeal): Completable
    fun count(date: List<Long>): Observable<List<CountResult>>
}