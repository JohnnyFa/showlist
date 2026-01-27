package com.fagundes.myshowlist.core.domain

data class Movie(
    override val id: Int,
    override val title: String,
    override val posterUrl: String?,
    override val overview: String?,
    override val rating: Double?
) : Content