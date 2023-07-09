package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.remote

import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategoriesResponseWrapper
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealResponseWrapper
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShortResponseWrapper

interface MealService {
    @GET("categories.php")
    fun getAll(): Observable<MealCategoriesResponseWrapper>

    @GET("search.php")
    fun searchByMealName(@Query("s") name: String): Observable<MealResponseWrapper>

    @GET("filter.php")
    fun searchByIngredient(@Query("i") name: String): Observable<MealShortResponseWrapper>

    @GET("filter.php")
    fun allMealsForCategory(@Query("c") category: String): Observable<MealShortResponseWrapper>

    @GET("filter.php")
    fun allMealsForArea(@Query("a") area: String): Observable<MealShortResponseWrapper>
    @GET("lookup.php")
    fun mealDetails(@Query("i") id: String): Observable<MealResponseWrapper>

}