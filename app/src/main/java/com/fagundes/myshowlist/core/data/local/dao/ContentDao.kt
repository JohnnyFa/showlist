package com.fagundes.myshowlist.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fagundes.myshowlist.core.data.local.entity.ContentEntity
import com.fagundes.myshowlist.core.data.local.enum.ContentCategory

@Dao
interface ContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(content: ContentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contents: List<ContentEntity>)

    @Query("SELECT * FROM content WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): ContentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ContentEntity)

    @Query("SELECT * FROM content WHERE category = :category AND lastUpdated > :maxAgeMillis")
    suspend fun getMoviesByCategory(category: ContentCategory, maxAgeMillis: Long): List<ContentEntity>
}
