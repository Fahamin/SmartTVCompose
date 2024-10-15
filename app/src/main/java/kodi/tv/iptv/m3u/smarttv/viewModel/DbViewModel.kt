package kodi.tv.iptv.m3u.smarttv.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kodi.tv.iptv.m3u.koditv.model.Player_FavModel
import kodi.tv.iptv.m3u.smarttv.model.ChannelModel
import kodi.tv.iptv.m3u.smarttv.model.PlayListModel
import kodi.tv.iptv.m3u.smarttv.repository.DbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DbViewModel @Inject constructor(private var repository: DbRepository) : ViewModel() {


    fun insertChannel(channelModel:List<ChannelModel> ) {
        repository.insertChannel(channelModel)
    }

    fun getChannelByID(id: String): MutableList<ChannelModel> {
        return repository.getChannelByID(id)
    }

    fun getAllChannel(): MutableList<ChannelModel> {
        return repository.getAllChannel()
    }


    fun insertPlaylist(noteModel: PlayListModel) {
        repository.insertPlaylist(noteModel)
    }

    fun checkPlayList(id: String): Boolean {
        return repository.checkPlayList(id)
    }

    fun getAllPlayList(): MutableList<PlayListModel> {
        return repository.getAllPlayList()
    }

    fun insertFav(noteModel: Player_FavModel) {
        repository.insertFav(noteModel)
    }

    fun getAllFav(): MutableList<Player_FavModel> {
        return repository.getAllFav()
    }

    fun isFavorite(id: Int): Int {
        return repository.isFavorite(id)
    }

    fun delete(noteModel: Player_FavModel) {
        repository.delete(noteModel)
    }

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _persons = MutableStateFlow(repository.getAllChannel())
    val persons = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_persons) { text, persons ->
            if (text.isBlank()) {
                persons
            } else {
                persons.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _persons.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

}