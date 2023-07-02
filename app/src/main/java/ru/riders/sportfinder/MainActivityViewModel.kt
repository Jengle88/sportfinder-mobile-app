package ru.riders.sportfinder

import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    var isForceAuth = false
        private set

    fun enableForceAuth() {
        isForceAuth = true
    }
}