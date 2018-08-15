package com.roudykk.domain.model

data class MovieGroup(val title: String,
                      val movies: List<Movie>,
                      val page: Int,
                      val totalPages: Int,
                      val index: String)