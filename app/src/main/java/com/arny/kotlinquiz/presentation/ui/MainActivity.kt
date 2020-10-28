package com.arny.kotlinquiz.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.arny.kotlinquiz.R
import com.arny.kotlinquiz.databinding.MainActivityBinding
import com.arny.kotlinquiz.presentation.ui.main.MainFragment

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    private val binding by viewBinding(MainActivityBinding::bind, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}