package com.roudykk.data.model

data class MovieGroupEntity(val title: String,
                            val movies: List<MovieEntity>,
                            val index: String)