package com.demo.roudykk.demoapp.extensions

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.demo.roudykk.demoapp.DemoApplication

@MainThread
inline fun <reified VM : ViewModel> Fragment.viewModel(): Lazy<VM>  {
    return viewModels(factoryProducer = { DemoApplication.instance.viewModelFactory })
}