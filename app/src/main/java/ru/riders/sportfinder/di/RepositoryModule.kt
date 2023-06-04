package ru.riders.sportfinder.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.riders.sportfinder.data.repository.RunningTracksRepositoryImpl
import ru.riders.sportfinder.data.repository.SportCourtsListRepositoryImpl
import ru.riders.sportfinder.data.repository.UserProfileRepositoryImpl
import ru.riders.sportfinder.domain.repository.RunningTracksRepository
import ru.riders.sportfinder.domain.repository.SportCourtsListRepository
import ru.riders.sportfinder.domain.repository.UserProfileRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserProfileRepository(
        userProfileRepositoryImpl: UserProfileRepositoryImpl): UserProfileRepository

    @Binds
    abstract fun bindSportCourtListRepository(
        sportCourtsListRepositoryImpl: SportCourtsListRepositoryImpl
        ): SportCourtsListRepository

    @Binds
    abstract fun bindRunningTracksRepository(
        runningTracksRepositoryImpl: RunningTracksRepositoryImpl
    ): RunningTracksRepository
}
