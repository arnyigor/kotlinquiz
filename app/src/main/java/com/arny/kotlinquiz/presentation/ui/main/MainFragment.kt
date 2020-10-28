package com.arny.kotlinquiz.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.arny.kotlinquiz.R
import com.arny.kotlinquiz.databinding.MainFragmentBinding
import com.arny.kotlinquiz.presentation.utils.viewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val model by viewModel<MainViewModel>()

    private val binding by viewBinding(MainFragmentBinding::bind)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindingUI()
        observeUI()
        model.launchDataLoad()
    }

    private fun observeUI() {
        model.message.observe(viewLifecycleOwner) {
            binding.message.text = it
        }
    }

    private fun bindingUI() {
        binding.message.setOnClickListener {
            model.launchDataLoad()
        }
    }
}
