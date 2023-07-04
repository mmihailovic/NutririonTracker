package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.remote

import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategoriesResponseWrapper
import io.reactivex.Observable
import retrofit2.http.GET

interface MealService {
    @GET("categories.php")
    fun getAll(): Observable<MealCategoriesResponseWrapper>
}