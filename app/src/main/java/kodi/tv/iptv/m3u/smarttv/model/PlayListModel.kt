package kodi.tv.iptv.m3u.smarttv.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PlayListTable")
data class PlayListModel(
    @PrimaryKey
    var idPlayList: String = "",

    @ColumnInfo(name = "name")
    var namePlayList: String = "",

    @ColumnInfo(name = "totalChannel")
    var totalChannel: Int=0,

)