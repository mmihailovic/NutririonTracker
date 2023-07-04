package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories

import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategory
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Resource
import io.reactivex.Completable
import io.reactivex.Observable

interface MealCategoryRepository {
    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<MealCategory>>
    fun insert(meal: MealCategory): Completable
}