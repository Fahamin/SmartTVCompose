package kodi.tv.iptv.m3u.smarttv.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ChannelTable")

data class ChannelModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "playlistid")
    var playListId: String = "",


    @ColumnInfo(name = "fav")
    var fav: Int = 0,


    @ColumnInfo(name = "logoUrl")
    var logoUrl: String = "",


    @ColumnInfo(name = "mime_type")
    var mime_type: String = "",


    @ColumnInfo(name = "category")
    var category: String = "",


    @ColumnInfo(name = "path")
    var path: String = "",

    @ColumnInfo(name = "title")
    var title: String = "",


    ){
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            title
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}