package rs.raf.projekat_jul_marko_mihailovic_rn252020.modules

import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.MainViewModel
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local.MealCategoryDatabase
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.remote.MealService
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.MealCategoryRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.MealCategoryRepositoryImplementation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.MealRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.MealRepositoryImplementation
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.MealShortRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.MealShortRepositoryImplementation
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.IngredientViewModel

val mealModule = module {

    viewModel { MainViewModel(mealCategoryRepository = get(), mealRepository = get()) }
    viewModel { IngredientViewModel(mealRepository = get())}
    single<MealCategoryRepository> { MealCategoryRepositoryImplementation(localDataSource = get(), remoteDataSource = get()) }
    single<MealShortRepository> {MealShortRepositoryImplementation(localDataSource = get(), remoteDataSource = get())}
    single<MealRepository> {MealRepositoryImplementation(localDataSource = get(), remoteDataSource = get())}
    single { get<MealCategoryDatabase>().getMealCategoryDao() }
    single { get<MealCategoryDatabase>().getMealDao() }
    single {get<MealCategoryDatabase>().getMealShortDao()}
    single<MealService> { create(retrofit = get()) }

}