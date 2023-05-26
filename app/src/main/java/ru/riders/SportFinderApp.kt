package ru.riders

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp
import ru.riders.sportfinder.common.Constants


@HiltAndroidApp
class SportFinderApp: Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(Constants.YANDEX_MAP_APIKEY)
    }
}