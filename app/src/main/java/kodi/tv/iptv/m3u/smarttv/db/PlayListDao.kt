package kodi.tv.iptv.m3u.smarttv.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kodi.tv.iptv.m3u.koditv.model.PlayListModel

@Dao
interface PlayListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaylist(playListModel: PlayListModel)

    @Query("select * from PlayListTable")
    fun getAllPlayList(): MutableList<PlayListModel>

    @Query("SELECT EXISTS (SELECT 1 FROM PlayListTable WHERE idPlayList=:idPlayList)")
    fun checkPlayList(idPlayList: String): Boolean

    @Query("DELETE FROM PlayListTable WHERE idPlayList = :idPlayList")
    fun deleteByPlayList(idPlayList: String)

    @Delete
    fun delete(playListModel: PlayListModel)
}