package com.verona1024.ttestapp.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import com.verona1024.ttestapp.R
import com.verona1024.ttestapp.databinding.MainFragmentBinding
import com.verona1024.ttestapp.presentation.base.BaseFragment

class MainFragment : BaseFragment<MainFragmentBinding, MainViewModel>(
    R.layout.main_fragment,
    MainViewModel::class.java
) {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun getBinding(inflater: LayoutInflater) = MainFragmentBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getClockHash()?.observe(viewLifecycleOwner, Observer {
            binding.message.text = getString(R.string.message_title)

            for (index in it.indices) {
                binding.message.append(String.format("${index + 1}. ${it[index].first}: ${it[index].second}\n"))
            }
        })
    }
}
