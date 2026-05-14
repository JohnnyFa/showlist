package com.fagundes.myshowlist.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fagundes.myshowlist.core.data.local.enum.ContentType

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: Int,
    val type: ContentType,
    val title: String,
    val posterUrl: String?,
    val overview: String?,
    val rating: Double?,
    val favoritedAt: Long
)
