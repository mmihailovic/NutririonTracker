package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories

import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local.MealDao
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.remote.MealService
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategory
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealCategoryEntity
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Resource
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber

class MealRepositoryImplementation(
    private val localDataSource: MealDao,
    private val remoteDataSource: MealService
): MealRepository {
    override fun fetchAll(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAll()
            .doOnNext {
                Timber.e("Upis u bazu")
                val entities = it.categories.map {
                    MealCategoryEntity(
                        it.idCategory,
                        it.strCategory,
                        it.strCategoryThumb,
                        it.strCategoryDescription
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAll(): Observable<List<MealCategory>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    MealCategory(it.id,it.category,it.thumbnail,it.description)
                }
            }
    }

    override fun insert(meal: MealCategory): Completable {
        val movieEntity = MealCategoryEntity(meal.id, meal.category, meal.thumbnail, meal.description)
        return localDataSource
            .insert(movieEntity)
    }
}