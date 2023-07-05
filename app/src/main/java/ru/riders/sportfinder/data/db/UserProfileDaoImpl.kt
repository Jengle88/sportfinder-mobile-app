package ru.riders.sportfinder.data.db

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserProfileDaoImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserProfileDao {
    private val tokenKey = stringPreferencesKey("token")
    override suspend fun getUserToken(): String? {
        val token = dataStore.data.first()
        Log.d("UserProfileDaoImpl", "Token has got - ${token[tokenKey]}")
        return token[tokenKey]
    }

    override suspend fun insertUserProfile(userToken: String) {
        dataStore.edit { userProfile ->
            userProfile[tokenKey] = userToken
            Log.d("UserProfileDaoImpl", "Token has inserted - $userToken")
        }
    }

    override suspend fun deleteUserProfile() {
        insertUserProfile("")
        Log.d("UserProfileDaoImpl", "Token has removed")
    }
}