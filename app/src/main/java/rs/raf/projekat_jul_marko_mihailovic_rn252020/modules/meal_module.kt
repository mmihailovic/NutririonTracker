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

val mealModule = module {

    viewModel { MainViewModel(mealCategoryRepository = get(), mealRepository = get()) }

    single<MealCategoryRepository> { MealCategoryRepositoryImplementation(localDataSource = get(), remoteDataSource = get()) }

    single<MealRepository> {MealRepositoryImplementation(localDataSource = get(), remoteDataSource = get())}
    single { get<MealCategoryDatabase>().getMealCategoryDao() }
    single { get<MealCategoryDatabase>().getMealDao() }
    single<MealService> { create(retrofit = get()) }

}