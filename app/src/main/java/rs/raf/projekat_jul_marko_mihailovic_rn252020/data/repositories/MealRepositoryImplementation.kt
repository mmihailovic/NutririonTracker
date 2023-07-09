package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local.MealDao
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.remote.MealService
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Meal
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealEntity
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Resource

class MealRepositoryImplementation(
    private val localDataSource: MealDao,
    private val remoteDataSource: MealService
): MealRepository {
    override fun fetchMealByName(name: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .searchByMealName(name)
            .doOnNext {
                val entities = it.meals.map {
                    MealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strDrinkAlternate,
                        it.strCategory,
                        it.strArea,
                        it.strInstructions,
                        it.strMealThumb,
                        it.strTags,
                        it.strYoutube,
                        it.strIngredient1,
                        it.strIngredient2,
                        it.strIngredient3,
                        it.strIngredient4,
                        it.strIngredient5,
                        it.strIngredient6,
                        it.strIngredient7,
                        it.strIngredient8,
                        it.strIngredient9,
                        it.strIngredient10,
                        it.strIngredient11,
                        it.strIngredient12,
                        it.strIngredient13,
                        it.strIngredient14,
                        it.strIngredient15,
                        it.strIngredient16,
                        it.strIngredient17,
                        it.strIngredient18,
                        it.strIngredient19,
                        it.strIngredient20,
                        it.strMeasure1,
                        it.strMeasure2,
                        it.strMeasure3,
                        it.strMeasure4,
                        it.strMeasure5,
                        it.strMeasure6,
                        it.strMeasure7,
                        it.strMeasure8,
                        it.strMeasure9,
                        it.strMeasure10,
                        it.strMeasure11,
                        it.strMeasure12,
                        it.strMeasure13,
                        it.strMeasure14,
                        it.strMeasure15,
                        it.strMeasure16,
                        it.strMeasure17,
                        it.strMeasure18,
                        it.strMeasure19,
                        it.strMeasure20
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAll(): Observable<List<Meal>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Meal(
                        it.idMeal,
                        it.strMeal,
                        it.strDrinkAlternate,
                        it.strCategory,
                        it.strArea,
                        it.strInstructions,
                        it.strMealThumb,
                        it.strTags,
                        it.strYoutube,
                        it.strIngredient1,
                        it.strIngredient2,
                        it.strIngredient3,
                        it.strIngredient4,
                        it.strIngredient5,
                        it.strIngredient6,
                        it.strIngredient7,
                        it.strIngredient8,
                        it.strIngredient9,
                        it.strIngredient10,
                        it.strIngredient11,
                        it.strIngredient12,
                        it.strIngredient13,
                        it.strIngredient14,
                        it.strIngredient15,
                        it.strIngredient16,
                        it.strIngredient17,
                        it.strIngredient18,
                        it.strIngredient19,
                        it.strIngredient20,
                        it.strMeasure1,
                        it.strMeasure2,
                        it.strMeasure3,
                        it.strMeasure4,
                        it.strMeasure5,
                        it.strMeasure6,
                        it.strMeasure7,
                        it.strMeasure8,
                        it.strMeasure9,
                        it.strMeasure10,
                        it.strMeasure11,
                        it.strMeasure12,
                        it.strMeasure13,
                        it.strMeasure14,
                        it.strMeasure15,
                        it.strMeasure16,
                        it.strMeasure17,
                        it.strMeasure18,
                        it.strMeasure19,
                        it.strMeasure20
                    )
                }
            }
    }

    override fun find(id: String): Observable<Meal> {
        return localDataSource
            .find(id)
            .map {
                Meal(
                    it.idMeal,
                    it.strMeal,
                    it.strDrinkAlternate,
                    it.strCategory,
                    it.strArea,
                    it.strInstructions,
                    it.strMealThumb,
                    it.strTags,
                    it.strYoutube,
                    it.strIngredient1,
                    it.strIngredient2,
                    it.strIngredient3,
                    it.strIngredient4,
                    it.strIngredient5,
                    it.strIngredient6,
                    it.strIngredient7,
                    it.strIngredient8,
                    it.strIngredient9,
                    it.strIngredient10,
                    it.strIngredient11,
                    it.strIngredient12,
                    it.strIngredient13,
                    it.strIngredient14,
                    it.strIngredient15,
                    it.strIngredient16,
                    it.strIngredient17,
                    it.strIngredient18,
                    it.strIngredient19,
                    it.strIngredient20,
                    it.strMeasure1,
                    it.strMeasure2,
                    it.strMeasure3,
                    it.strMeasure4,
                    it.strMeasure5,
                    it.strMeasure6,
                    it.strMeasure7,
                    it.strMeasure8,
                    it.strMeasure9,
                    it.strMeasure10,
                    it.strMeasure11,
                    it.strMeasure12,
                    it.strMeasure13,
                    it.strMeasure14,
                    it.strMeasure15,
                    it.strMeasure16,
                    it.strMeasure17,
                    it.strMeasure18,
                    it.strMeasure19,
                    it.strMeasure20
                )
            }
    }

    override fun insert(meal: Meal): Completable {
        val movieEntity = MealEntity(
            meal.idMeal,
            meal.strMeal,
            meal.strDrinkAlternate,
            meal.strCategory,
            meal.strArea,
            meal.strInstructions,
            meal.strMealThumb,
            meal.strTags,
            meal.strYoutube,
            meal.strIngredient1,
            meal.strIngredient2,
            meal.strIngredient3,
            meal.strIngredient4,
            meal.strIngredient5,
            meal.strIngredient6,
            meal.strIngredient7,
            meal.strIngredient8,
            meal.strIngredient9,
            meal.strIngredient10,
            meal.strIngredient11,
            meal.strIngredient12,
            meal.strIngredient13,
            meal.strIngredient14,
            meal.strIngredient15,
            meal.strIngredient16,
            meal.strIngredient17,
            meal.strIngredient18,
            meal.strIngredient19,
            meal.strIngredient20,
            meal.strMeasure1,
            meal.strMeasure2,
            meal.strMeasure3,
            meal.strMeasure4,
            meal.strMeasure5,
            meal.strMeasure6,
            meal.strMeasure7,
            meal.strMeasure8,
            meal.strMeasure9,
            meal.strMeasure10,
            meal.strMeasure11,
            meal.strMeasure12,
            meal.strMeasure13,
            meal.strMeasure14,
            meal.strMeasure15,
            meal.strMeasure16,
            meal.strMeasure17,
            meal.strMeasure18,
            meal.strMeasure19,
            meal.strMeasure20
        )
        return localDataSource
            .insert(movieEntity)
    }

    override fun fetchSingleMeal(id: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .mealDetails(id)
            .doOnNext {
                val entities = it.meals.map {
                    MealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strDrinkAlternate,
                        it.strCategory,
                        it.strArea,
                        it.strInstructions,
                        it.strMealThumb,
                        it.strTags,
                        it.strYoutube,
                        it.strIngredient1,
                        it.strIngredient2,
                        it.strIngredient3,
                        it.strIngredient4,
                        it.strIngredient5,
                        it.strIngredient6,
                        it.strIngredient7,
                        it.strIngredient8,
                        it.strIngredient9,
                        it.strIngredient10,
                        it.strIngredient11,
                        it.strIngredient12,
                        it.strIngredient13,
                        it.strIngredient14,
                        it.strIngredient15,
                        it.strIngredient16,
                        it.strIngredient17,
                        it.strIngredient18,
                        it.strIngredient19,
                        it.strIngredient20,
                        it.strMeasure1,
                        it.strMeasure2,
                        it.strMeasure3,
                        it.strMeasure4,
                        it.strMeasure5,
                        it.strMeasure6,
                        it.strMeasure7,
                        it.strMeasure8,
                        it.strMeasure9,
                        it.strMeasure10,
                        it.strMeasure11,
                        it.strMeasure12,
                        it.strMeasure13,
                        it.strMeasure14,
                        it.strMeasure15,
                        it.strMeasure16,
                        it.strMeasure17,
                        it.strMeasure18,
                        it.strMeasure19,
                        it.strMeasure20
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }
}