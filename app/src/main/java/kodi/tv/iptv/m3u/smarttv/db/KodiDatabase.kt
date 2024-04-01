package kodi.tv.iptv.m3u.smarttv.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kodi.tv.iptv.m3u.koditv.model.FavModel
import kodi.tv.iptv.m3u.smarttv.model.PlayListModel
import kodi.tv.iptv.m3u.koditv.model.Player_FavModel
import kodi.tv.iptv.m3u.smarttv.model.ChannelModel

@Database(
    entities = [Player_FavModel::class, PlayListModel::class, FavModel::class, ChannelModel::class],
    version = 6
)
abstract class KodiDatabase : RoomDatabase() {
    abstract fun channelFavDao(): ChannelFavDao
    abstract fun channelModelDao(): ChannelModelDao
    abstract fun playlistDao(): PlayListDao

}
