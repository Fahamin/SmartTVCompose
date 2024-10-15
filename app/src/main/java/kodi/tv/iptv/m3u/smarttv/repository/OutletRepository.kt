package kodi.tv.iptv.m3u.smarttv.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kodi.tv.iptv.m3u.smarttv.model.M3uModel
import kodi.tv.iptv.m3u.smarttv.utils.NetworkResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class M3uModelRepository @Inject constructor(
    private val database: FirebaseDatabase
) {

    fun getM3uModelList(): Flow<NetworkResult<List<M3uModel>>> = flow {
        emit(NetworkResult.Loading()) // Emit loading state

        try {
            val dataSnapshot = database.getReference("mm").get().await()
            val dataList = mutableListOf<M3uModel>()

            for (snapshot in dataSnapshot.children) {
                val model = snapshot.getValue(M3uModel::class.java)
                model?.let { dataList.add(it) }
            }

            emit(NetworkResult.Success(dataList)) // Emit success state
        } catch (e: Exception) {
            emit(NetworkResult.Error("Error fetching data: ${e.localizedMessage}")) // Emit error state
        }
    }
}
