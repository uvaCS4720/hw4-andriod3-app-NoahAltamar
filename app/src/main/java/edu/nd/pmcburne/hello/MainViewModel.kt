package edu.nd.pmcburne.hello

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = PlacemarkDatabase.getDatabase(application).placemarkDao()

    private val _selectedTag = MutableStateFlow("core")
    val selectedTag: StateFlow<String> = _selectedTag.asStateFlow()

    private val _allTags = MutableStateFlow<List<String>>(emptyList())
    val allTags: StateFlow<List<String>> = _allTags.asStateFlow()

    private val _filteredPlacemarks = MutableStateFlow<List<Placemark>>(emptyList())
    val filteredPlacemarks: StateFlow<List<Placemark>> = _filteredPlacemarks.asStateFlow()

    init {
        fetchAndStore()
        observePlacemarks()
    }

    private fun fetchAndStore() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getPlacemarks()
                val placemarks = response.map { r ->
                    Placemark(
                        id = r.id,
                        name = r.name,
                        description = r.description,
                        tagList = r.tagList,
                        latitude = r.visualCenter.latitude,
                        longitude = r.visualCenter.longitude
                    )
                }
                dao.insertAll(placemarks)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun observePlacemarks() {
        viewModelScope.launch {
            dao.getAllPlacemarks().flatMapLatest { placemarks ->
                _allTags.value = placemarks
                    .flatMap { it.tagList }
                    .distinct()
                    .sorted()

                _selectedTag.flatMapLatest { tag ->
                    kotlinx.coroutines.flow.flow {
                        emit(placemarks.filter { it.tagList.contains(tag) })
                    }
                }
            }.collect { filtered ->
                _filteredPlacemarks.value = filtered
            }
        }
    }

    fun selectTag(tag: String) {
        _selectedTag.value = tag
    }
}