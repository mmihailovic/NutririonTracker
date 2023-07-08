package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local.MealShortDao
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.remote.MealService
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShort
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealShortEntity
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Resource
import timber.log.Timber

class MealShortRepositoryImplementation(
    private val localDataSource: MealShortDao,
    private val remoteDataSource: MealService
): MealShortRepository {
    override fun fetchAllByIngredient(name: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .searchByIngredient(name)
            .doOnNext {
                Timber.e("Upis u bazu")
                val entities = it.meals.map {
                    MealShortEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strMealThumb,
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun fetchAllByCategory(name: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .allMealsForCategory(name)
            .doOnNext {
                val entities = it.meals.map {
                    MealShortEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strMealThumb
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAll(): Observable<List<MealShort>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    MealShort(
                        it.idMeal,
                        it.strMeal,
                        it.strMealThumb
                    )
                }
            }
    }

    override fun getAllWithPagination(pocetak: Int): Observable<List<MealShort>> {
        return localDataSource
            .getAllWithPagination(pocetak)
            .map {
                it.map {
                    MealShort(
                        it.idMeal,
                        it.strMeal,
                        it.strMealThumb
                    )
                }
            }
    }

    override fun insert(meal: MealShort): Completable {
        val movieEntity = MealShortEntity(
            meal.idMeal,
            meal.strMeal,
            meal.strMealThumb
        )
        return localDataSource
            .insert(movieEntity)
    }

}