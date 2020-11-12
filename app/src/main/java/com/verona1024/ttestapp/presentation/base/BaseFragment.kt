package com.verona1024.ttestapp.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(
    fragmentRef: Int,
    private val viewModelType: Class<out BaseViewModel>
) :
    Fragment(fragmentRef) {

    private var _binding: ViewBinding? = null
    private lateinit var _viewModel: BaseViewModel

    // While view is present binding would not be null,
    // We could skip !! for clear usage.
    protected val binding get() = _binding!! as VB
    protected val viewModel get() = _viewModel as VM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getBinding(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel = ViewModelProvider.NewInstanceFactory().create(viewModelType)
        _viewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun getBinding(inflater: LayoutInflater): ViewBinding
}