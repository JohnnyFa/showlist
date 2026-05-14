package com.fagundes.myshowlist.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fagundes.myshowlist.core.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun remove(id: Int)

    @Query("SELECT * FROM favorites WHERE id = :id LIMIT 1")
    fun observeById(id: Int): Flow<FavoriteEntity?>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean

    @Query("SELECT * FROM favorites ORDER BY favoritedAt DESC")
    fun observeAllFavorites(): Flow<List<FavoriteEntity>>
}
