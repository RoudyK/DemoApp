package com.demo.roudykk.demoapp.ui.fragment

import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar

abstract class BaseFragment : Fragment() {
    open val supportsFabAction: Boolean = false
    open val fabAlignmentMode: Int = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
    open val fabAction: () -> Unit = {}
}