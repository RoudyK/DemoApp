package com.roudykk.data.model

data class MovieGroupEntity(val title: String,
                            val index: String,
                            val movies: List<MovieEntity>)