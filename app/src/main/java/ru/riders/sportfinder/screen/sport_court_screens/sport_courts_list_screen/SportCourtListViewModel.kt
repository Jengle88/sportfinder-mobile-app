package ru.riders.sportfinder.screen.sport_court_screens.sport_courts_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.domain.model.sport_court.SportCourtForList
import ru.riders.sportfinder.domain.model.sport_court.toSportCourtForList
import ru.riders.sportfinder.domain.use_case.LoadSportCourtsList
import javax.inject.Inject

@HiltViewModel
class SportCourtListViewModel @Inject constructor(
    private val loadSportCourtsList: LoadSportCourtsList
): ViewModel() {

    private val _listSportCourts = mutableStateOf(emptyList<SportCourtForList>())
    val listSportCourts: State<List<SportCourtForList>> = _listSportCourts

    private var _isLoading = mutableStateOf(false)
    val isLoading = _isLoading as State<Boolean>

    init {
        updateListSportCourts()
    }

    private fun updateListSportCourts() {
        loadSportCourtsList.invoke().onEach { result ->
            when (result) {
                is ApiResultState.Loading -> {
                    _isLoading.value = true
                }

                is ApiResultState.Error -> {

                }

                is ApiResultState.Success -> {
                    _isLoading.value = false
                    _listSportCourts.value = (result.data ?: emptyList()).map { it.toSportCourtForList() }
                }
            }
        }.launchIn(viewModelScope)
    }
}
