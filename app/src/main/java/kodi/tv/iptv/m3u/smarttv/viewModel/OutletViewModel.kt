package kodi.tv.iptv.m3u.smarttv.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kodi.tv.iptv.m3u.smarttv.model.M3uModel
import kodi.tv.iptv.m3u.smarttv.repository.M3uModelRepository
import kodi.tv.iptv.m3u.smarttv.utils.NetworkResult
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class OutletViewModel @Inject constructor(
    private val outletRepository: M3uModelRepository
) : ViewModel() {

    private val _outletResult = MutableStateFlow<NetworkResult<List<M3uModel>>>(NetworkResult.Loading())
    val outletResult: StateFlow<NetworkResult<List<M3uModel>>> get() = _outletResult

    fun fetchOutlets() {
        viewModelScope.launch {
            outletRepository.getM3uModelList().collect { result ->
                _outletResult.value = result
            }
        }
    }
}
