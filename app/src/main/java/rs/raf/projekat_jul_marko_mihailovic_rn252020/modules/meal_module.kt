package rs.raf.projekat_jul_marko_mihailovic_rn252020.modules

import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.MainViewModel
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local.MealCategoryDatabase
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.remote.MealService
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.MealRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.MealRepositoryImplementation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mealModule = module {

    viewModel { MainViewModel(mealRepository = get()) }

    single<MealRepository> { MealRepositoryImplementation(localDataSource = get(), remoteDataSource = get()) }

    single { get<MealCategoryDatabase>().getMealDao() }

    single<MealService> { create(retrofit = get()) }

}