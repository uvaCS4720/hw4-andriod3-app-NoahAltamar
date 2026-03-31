package edu.nd.pmcburne.hello

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlacemarkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(placemarks: List<Placemark>)

    @Query("SELECT * FROM placemarks")
    fun getAllPlacemarks(): Flow<List<Placemark>>
}