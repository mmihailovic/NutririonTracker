package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.remote

import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategoriesResponseWrapper
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealResponseWrapper

interface MealService {
    @GET("categories.php")
    fun getAll(): Observable<MealCategoriesResponseWrapper>

    @GET("search.php")
    fun searchByMealName(@Query("s") name: String): Observable<MealResponseWrapper>
}