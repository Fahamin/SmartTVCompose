package kodi.tv.iptv.m3u.smarttv.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class PlayerViewModel : ViewModel() {
    private val _selectedUrl = MutableLiveData<String>()
    val selectedUrl: LiveData<String> = _selectedUrl

    fun selectUrl(url: String) {
        _selectedUrl.value = url
    }
}