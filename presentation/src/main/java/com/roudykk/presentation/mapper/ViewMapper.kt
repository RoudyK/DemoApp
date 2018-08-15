package com.roudykk.presentation.mapper

interface ViewMapper<V, D> {

    fun mapFromView(view: V): D

    fun mapToView(domain: D): V
}