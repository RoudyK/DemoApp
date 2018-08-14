package com.roudykk.remote.model

data class PersonModel(
        var id: Int,
        var name: String,
        var cast_id: String,
        var credit_id: String,
        var character: String,
        var gender: Int,
        var order: Int,
        var profile_path: String? = null,
        var birthday: String? = null,
        var deathday: String? = null,
        var known_for_department: String? = null,
        var biography: String? = null,
        var popularity: Float,
        var place_of_birth: String? = null,
        var adult: Boolean,
        var also_known_as: ArrayList<String>)