package com.fagundes.myshowlist.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fagundes.myshowlist.core.data.local.enum.ContentCategory
import com.fagundes.myshowlist.core.data.local.enum.ContentType

@Entity(tableName = "content")
data class ContentEntity(
    @PrimaryKey val id: Int,
    val type: ContentType,
    val title: String,
    val posterUrl: String?,
    val overview: String?,
    val rating: Double?,
    val category: ContentCategory,
    val lastUpdated: Long
)