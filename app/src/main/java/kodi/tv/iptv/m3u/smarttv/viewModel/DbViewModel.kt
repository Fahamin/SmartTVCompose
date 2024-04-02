package kodi.tv.iptv.m3u.smarttv.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kodi.tv.iptv.m3u.smarttv.model.PlayListModel
import kodi.tv.iptv.m3u.koditv.model.Player_FavModel
import kodi.tv.iptv.m3u.smarttv.model.ChannelModel
import kodi.tv.iptv.m3u.smarttv.repository.DbRepository
import javax.inject.Inject

@HiltViewModel
class DbViewModel @Inject constructor(private var repository: DbRepository) : ViewModel() {


    fun insertChannel(channelModel: ChannelModel) {
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

}