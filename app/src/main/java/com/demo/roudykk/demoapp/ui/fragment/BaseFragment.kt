package com.demo.roudykk.demoapp.ui.fragment

import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar

abstract class BaseFragment : Fragment(), BaseFragmentBuilder

interface BaseFragmentBuilder {
    fun supportsFabAction(): Boolean = false
    fun fabAlignmentMode(): Int = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
    fun fabAction(): () -> Unit = {}
    fun fabIconRes(): Int? = null
}