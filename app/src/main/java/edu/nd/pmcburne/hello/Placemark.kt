package edu.nd.pmcburne.hello

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TagListConverter {
    @TypeConverter
    fun fromTagList(tags: List<String>): String {
        return Gson().toJson(tags)
    }

    @TypeConverter
    fun toTagList(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type)
    }
}

@Entity(tableName = "placemarks")
@TypeConverters(TagListConverter::class)
data class Placemark(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val tagList: List<String>,
    val latitude: Double,
    val longitude: Double
)