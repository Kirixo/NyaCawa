package com.project.nyacawa.domain.logic

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

class SearchViewModel: ViewModel() {

    val searchText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private val _uiState = MutableStateFlow(SearchData())
    val uiState: StateFlow<SearchData> = _uiState.asStateFlow()

    init {
        searchText.observeForever{ newText ->
            _uiState.value = SearchData(searchText = newText, getSearchedAnimeList())
        }
    }

    private fun getSearchedAnimeList() : List<AnimeData> {
        TODO(" НУЖНО СДЕЛАТЬ КАК АНИМЕ ДОСТАВАТЬ С БД")
        return emptyList()
    }



}