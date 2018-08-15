package com.roudykk.presentation.model

data class PersonView(
        var id: Int,
        var name: String,
        var castId: String,
        var creditId: String,
        var character: String,
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
        var knownAs: ArrayList<String>)