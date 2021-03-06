package com.roudykk.presentation.state

class Resource<out T> constructor(
        val status: ResourceState,
        val data: T?,
        val message: String?)