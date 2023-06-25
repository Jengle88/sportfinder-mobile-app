package ru.riders.sportfinder.screen.sport_court_screens.sport_courts_map_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.domain.model.sport_court.SportCourtForMap
import ru.riders.sportfinder.domain.model.sport_court.toSportCourtForMap
import ru.riders.sportfinder.domain.use_case.LoadSportCourtsList
import javax.inject.Inject

@HiltViewModel
class SportCourtsMapViewModel @Inject constructor(
    private val loadSportCourtsList: LoadSportCourtsList
) : ViewModel() {

    private val _listSportCourts = mutableStateOf(emptyList<SportCourtForMap>())
    val listSportCourts: State<List<SportCourtForMap>> = _listSportCourts

    init {
        updateListSportCourts()
    }

    private fun updateListSportCourts() {
        loadSportCourtsList.invoke().onEach { result ->
            when (result) {
                is ApiResultState.Loading -> {

                }

                is ApiResultState.Error -> {

                }

                is ApiResultState.Success -> {
                    _listSportCourts.value = (result.data ?: emptyList()).map { it.toSportCourtForMap() }
                }
            }
        }.launchIn(viewModelScope)
    }
}