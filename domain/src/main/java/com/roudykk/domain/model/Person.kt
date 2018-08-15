package com.roudykk.domain.model

data class Person(
        var id: Int,
        var name: String? = null,
        var castId: String? = null,
        var creditId: String? = null,
        var character: String? = null,
        var gender: Int,
        var order: Int,
        var profilePath: String? = null,
        var birthday: String? = null,
        var deathday: String? = null,
        var knownFor: String? = null,
        var biography: String? = null,
        var popularity: Float,
        var placeOfBirth: String? = null,
        var adult: Boolean,
        var knownAs: ArrayList<String>? = null)