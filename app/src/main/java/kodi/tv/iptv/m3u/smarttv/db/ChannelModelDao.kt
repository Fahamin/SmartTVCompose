package kodi.tv.iptv.m3u.smarttv.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kodi.tv.iptv.m3u.smarttv.model.ChannelModel

@Dao
interface ChannelModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChannel(channelModel: List<ChannelModel>)

    @Query("select * from ChannelTable")
    fun getAllChannel(): MutableList<ChannelModel>

    @Query("SELECT * FROM ChannelTable WHERE title LIKE  '%' || :search || '%'  ")
    suspend fun searchChannel(search:String): List<ChannelModel>?

    @Query("SELECT * FROM ChannelTable WHERE playListId = :playListId")
    fun getChannelByPlayList(playListId: String): MutableList<ChannelModel>

}