package kodi.tv.iptv.m3u.smarttv.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kodi.tv.iptv.m3u.smarttv.db.ChannelModelDao
import kodi.tv.iptv.m3u.smarttv.di.MainDispatcher
import kodi.tv.iptv.m3u.smarttv.model.ChannelModel
import kodi.tv.iptv.m3u.smarttv.repository.DbRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    dbRepository: DbRepository,
    private val channelDao: ChannelModelDao,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,

    ) : ViewModel() {

    private val _selectedChannel = MutableLiveData<ChannelModel>()
    val selectedChannel: LiveData<ChannelModel>
        get() = _selectedChannel

    fun setSelectedChannel(item: ChannelModel) {
        _selectedChannel.value = item
    }

    var catId: Int = 0

    private val _channelData = MutableLiveData<List<ChannelModel>>()
    val channelData: LiveData<List<ChannelModel>>
        get() = _channelData
    private val _isFavoriteChannel = MutableLiveData(false)

    val isFavoriteChannel: LiveData<Boolean>
        get() = _isFavoriteChannel

    fun callChannelDataByCatId() {
        viewModelScope.launch {
            withContext(mainDispatcher) {
                _channelData.value = channelDao.getAllChannel().map { it }
            }
        }
        Log.e("data", channelData.value.toString())
    }


}