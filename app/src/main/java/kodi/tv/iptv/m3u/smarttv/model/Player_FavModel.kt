package kodi.tv.iptv.m3u.koditv.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_FavModel")
data class Player_FavModel(
    @PrimaryKey
    var id_chaine: Int = 0,

    @ColumnInfo(name = "logoUrl")
    var logoUrl: String = "",

    @ColumnInfo(name = "path")
    var path: String = "",

    @ColumnInfo(name = "title")
    var title: String = ""

)