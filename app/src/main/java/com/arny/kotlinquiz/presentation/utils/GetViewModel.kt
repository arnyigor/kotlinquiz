package com.arny.kotlinquiz.presentation.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KProperty

class GetViewModel<out T : ViewModel>(private val clazz: Class<T>) {

    private var value: T? = null

    operator fun getValue(thisRef: FragmentActivity, property: KProperty<*>): T {
        if (value == null) {
            value = ViewModelProvider(thisRef).get(clazz)
        }

        return value!!
    }

    operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (value == null) {
            value = ViewModelProvider(thisRef).get(clazz)
        }

        return value!!
    }
}

inline fun <reified T : ViewModel> viewModel() = GetViewModel(T::class.java)