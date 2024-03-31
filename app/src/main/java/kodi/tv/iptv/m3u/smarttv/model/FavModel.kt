package kodi.tv.iptv.m3u.koditv.model

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favpoodel")
data class FavModel(
    @PrimaryKey
    var id: Int =0,
    @ColumnInfo(name = "prname")
    var name: String="",
    @ColumnInfo(name = "link")
    var link: String="",
    @ColumnInfo(name = "url")
    var url: String=""


)