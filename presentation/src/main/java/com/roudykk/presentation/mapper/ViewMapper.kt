package com.roudykk.presentation.mapper

interface ViewMapper<V, D> {

    fun mapToView(domain: D): V
}