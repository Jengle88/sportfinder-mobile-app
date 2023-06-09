package ru.riders.sportfinder.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.riders.sportfinder.domain.repository.RunningTracksRepository
import ru.riders.sportfinder.domain.repository.SportCourtsListRepository
import ru.riders.sportfinder.domain.repository.UserProfileRepository
import ru.riders.sportfinder.domain.use_case.CheckTokenValidity
import ru.riders.sportfinder.domain.use_case.CreateRunningTrack
import ru.riders.sportfinder.domain.use_case.GetTags
import ru.riders.sportfinder.domain.use_case.GetUserProfile
import ru.riders.sportfinder.domain.use_case.LoadRunningTrack
import ru.riders.sportfinder.domain.use_case.LoadRunningTracksList
import ru.riders.sportfinder.domain.use_case.LoadSportCourtsList
import ru.riders.sportfinder.domain.use_case.LogoutUserProfile
import ru.riders.sportfinder.domain.use_case.SignInUser
import ru.riders.sportfinder.domain.use_case.SignUpUser
import ru.riders.sportfinder.network.interceptor.TokenInterceptor

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideSignUpUser(
        tokenInterceptor: TokenInterceptor,
        userProfileRepository: UserProfileRepository
    ) = SignUpUser(userProfileRepository, tokenInterceptor::updateToken)

    @Provides
    fun provideSignInUser(
        tokenInterceptor: TokenInterceptor,
        userProfileRepository: UserProfileRepository
    ) = SignInUser(userProfileRepository, tokenInterceptor::updateToken)

    @Provides
    fun provideLogoutUser(
        tokenInterceptor: TokenInterceptor,
        userProfileRepository: UserProfileRepository
    ) = LogoutUserProfile(userProfileRepository, tokenInterceptor::updateToken)

    @Provides
    fun provideGetUserProfile(
        userProfileRepository: UserProfileRepository
    ) = GetUserProfile(userProfileRepository)

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
        userProfileRepository: UserProfileRepository
    ) = CheckTokenValidity(userProfileRepository)

    @Provides
    fun provideCreateRunningTrack(
        runningTracksRepository: RunningTracksRepository
    ) = CreateRunningTrack(runningTracksRepository)
}
