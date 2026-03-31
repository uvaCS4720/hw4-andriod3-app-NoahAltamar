package edu.nd.pmcburne.hello

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Placemark::class], version = 1)
@TypeConverters(TagListConverter::class)
abstract class PlacemarkDatabase : RoomDatabase() {
    abstract fun placemarkDao(): PlacemarkDao

    companion object {
        @Volatile
        private var INSTANCE: PlacemarkDatabase? = null

        fun getDatabase(context: Context): PlacemarkDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlacemarkDatabase::class.java,
                    "placemark_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}