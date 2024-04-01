package kodi.tv.iptv.m3u.smarttv.repository

import kodi.tv.iptv.m3u.koditv.model.PlayListModel
import kodi.tv.iptv.m3u.koditv.model.Player_FavModel
import kodi.tv.iptv.m3u.smarttv.db.ChannelFavDao
import kodi.tv.iptv.m3u.smarttv.db.ChannelModelDao
import kodi.tv.iptv.m3u.smarttv.db.PlayListDao
import kodi.tv.iptv.m3u.smarttv.model.ChannelModel
import javax.inject.Inject

class DbRepository @Inject constructor(
    private val channelDao: ChannelModelDao,
    private val playListDao: PlayListDao,
    private val channelFavDao: ChannelFavDao
) {
    fun insertChannel(noteModel: ChannelModel) = channelDao.insertChannel(noteModel)
    fun getChannelByID(id: String): MutableList<ChannelModel> = channelDao.getChannelByPlayList(id)
    fun getAllChannel() = channelDao.getAllChannel()


    fun insertPlaylist(noteModel: PlayListModel) = playListDao.insertPlaylist(noteModel)
    fun checkPlayList(id: String): Boolean = playListDao.checkPlayList(id)
    fun getAllPlayList() = playListDao.getAllPlayList()


    fun insertFav(noteModel: Player_FavModel) = channelFavDao.insertFav(noteModel)
    fun getAllFav(): MutableList<Player_FavModel> = channelFavDao.getAllFavorite()
    fun isFavorite(id: Int) = channelFavDao.isFavorite(id)

    fun delete(noteModel: Player_FavModel) = channelFavDao.delete(noteModel)

}