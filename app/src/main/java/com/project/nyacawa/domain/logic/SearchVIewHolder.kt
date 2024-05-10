package com.project.nyacawa.domain.logic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.nyacawa.data.AnimeData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow



data class SearchData(
    val searchText: String = "",
    val searchedAnimeDataList: List<AnimeData> = emptyList()
)

//Search view holder for implement LiveData to keep tracking updates on changes in the search string
class SearchViewModel: ViewModel() {

    // Field for text from search string
    val searchText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    // UI updaters fields
    private val _uiState = MutableStateFlow(SearchData())
    val uiState: StateFlow<SearchData> = _uiState.asStateFlow()

    init {
        //Realization of LiveData change tracking
        searchText.observeForever{ newText ->
            _uiState.value = SearchData(searchText = newText, getSearchedAnimeList())
            Log.d("[SEARCH VIEW MODEL]", "text $newText")
        }
    }

    // Method to get anime data by search
    private fun getSearchedAnimeList() : List<AnimeData> {
        //TODO: НУЖНО СДЕЛАТЬ КАК АНИМЕ ДОСТАВАТЬ С БД
        return emptyList()
    }

}