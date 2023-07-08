package rs.raf.projekat_jul_marko_mihailovic_rn252020.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.datasources.local.UserDatabase
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.UserRepository
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.repositories.UserRepositoryImplementation
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.UserViewModel

val userModule = module {
    viewModel { UserViewModel(userRepository = get()) }
    single<UserRepository> { UserRepositoryImplementation(localDataSource = get()) }
    single { get<UserDatabase>().getUserDao() }
}