package com.roudykk.data.model

data class MovieGroupEntity(val title: String,
                            val movies: List<MovieEntity>,
                            val page: Int,
                            val totalPages: Int,
                            val index: String)