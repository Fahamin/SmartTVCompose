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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchChannelViewModel @Inject constructor(
    private val channelDao: ChannelModelDao,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
) : ViewModel() {
    private val _channelData = MutableLiveData<List<ChannelModel>>()
    val channelData: LiveData<List<ChannelModel>>
        get() = _channelData


    fun searchChannel(keyword: String) {
        viewModelScope.launch {
            withContext(mainDispatcher) {
                _channelData.value = channelDao.getAllChannel().map { it }

                Log.e("search", channelData.value.toString())

            }
        }
    }

}