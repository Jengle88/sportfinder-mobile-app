package ru.riders.sportfinder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.riders.sportfinder.domain.model.SignUp

@Database(
    entities = [SignUp::class],
    version = 1
)
abstract class SportFinderDatabase: RoomDatabase() {

    abstract val userProfileDao: UserProfileDao

    companion object {
        const val DATABASE_NAME = "sport_finder_db"
    }
}