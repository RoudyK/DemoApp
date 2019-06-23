package com.demo.roudykk.demoapp.db

class PreferenceRepo {

    enum class Theme(val value: String) {
        LIGHT("light"), DARK("dark"), BURGUNDY("burgundy")
    }

    companion object {
        const val PREFERENCE_THEME = "PREFERENCE_THEME"
    }
}