package kodi.tv.iptv.m3u.smarttv.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kodi.tv.iptv.m3u.koditv.model.FavModel

@Dao
interface BuildDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addData(favModel: FavModel)

    @get:Query("select * from favpoodel")
    val favoriteData: List<FavModel>

    @Query("SELECT EXISTS (SELECT 1 FROM favpoodel WHERE id=:id)")
    fun isFavorite(id: Int): Int

    @Delete
    fun delete(favModel: FavModel)
}