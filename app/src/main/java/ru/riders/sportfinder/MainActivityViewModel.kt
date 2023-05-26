package ru.riders.sportfinder

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.riders.sportfinder.data.remote.ServerApi
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var serverApi: ServerApi
}
