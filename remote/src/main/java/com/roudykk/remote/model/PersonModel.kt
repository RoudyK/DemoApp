package com.roudykk.remote.model

data class PersonModel(
        var id: Int,
        var name: String? = null,
        var cast_id: String? = null,
        var credit_id: String? = null,
        var character: String? = null,
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
        var also_known_as: ArrayList<String>? = null)