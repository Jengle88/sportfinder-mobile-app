package ru.riders.sportfinder.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.riders.sportfinder.data.db.UserProfileDao
import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.domain.repository.RunningTracksRepository
import ru.riders.sportfinder.domain.repository.SportCourtsListRepository
import ru.riders.sportfinder.domain.repository.UserProfileRepository
import ru.riders.sportfinder.domain.use_case.CheckTokenValidity
import ru.riders.sportfinder.domain.use_case.GetTags
import ru.riders.sportfinder.domain.use_case.GetUserProfile
import ru.riders.sportfinder.domain.use_case.LoadRunningTrack
import ru.riders.sportfinder.domain.use_case.LoadRunningTracksList
import ru.riders.sportfinder.domain.use_case.LoadSportCourtsList
import ru.riders.sportfinder.domain.use_case.SignInUser
import ru.riders.sportfinder.domain.use_case.SignUpUser

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideSignUpUser(
        userProfileDao: UserProfileDao,
        userProfileRepository: UserProfileRepository
    ) = SignUpUser(userProfileDao, userProfileRepository)

    @Provides
    fun provideSignInUser(
        userProfileDao: UserProfileDao,
        userProfileRepository: UserProfileRepository
    ) = SignInUser(userProfileDao, userProfileRepository)

    @Provides
    fun provideGetUserProfile(
        userProfileDao: UserProfileDao,
        userProfileRepository: UserProfileRepository
    ) = GetUserProfile(userProfileDao, userProfileRepository)

    @Provides
    fun provideLoadSportCourtsList(
        sportCourtsListRepository: SportCourtsListRepository
    ) = LoadSportCourtsList(sportCourtsListRepository)

    @Provides
    fun provideLoadRunningTracksList(
        runningTracksRepository: RunningTracksRepository
    ) = LoadRunningTracksList(runningTracksRepository)

    @Provides
    fun provideLoadRunningTrack(
        runningTracksRepository: RunningTracksRepository
    ) = LoadRunningTrack(runningTracksRepository)

    @Provides
    fun provideGetTags() = GetTags()

    @Provides
    fun provideCheckTokenValidity(
        serverApi: ServerApi,
        userProfileDao: UserProfileDao
    ) = CheckTokenValidity(serverApi, userProfileDao)
}
