package kodi.tv.iptv.m3u.smarttv.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kodi.tv.iptv.m3u.koditv.model.Player_FavModel

@Dao
interface ChannelFavDao {
    @Insert
    fun insertFav(playerFavModel: Player_FavModel)

    @Query("SELECT * FROM PLAYER_FAVMODEL")
    fun getAllFavorite(): MutableList<Player_FavModel>

    @Query("SELECT EXISTS (SELECT 1 FROM Player_FavModel WHERE id_chaine=:id_chaine)")
    fun isFavorite(id_chaine: Int): Int

    @Delete
    fun delete(playerFavModel: Player_FavModel)
}