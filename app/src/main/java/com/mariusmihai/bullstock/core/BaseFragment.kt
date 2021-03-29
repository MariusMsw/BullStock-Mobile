package com.mariusmihai.bullstock.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.mariusmihai.bullstock.BR
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    protected abstract val layout: Int
    protected abstract val viewModel: ViewModel
    protected val disposable = CompositeDisposable()

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onStop() {
        disposable.clear()
        super.onStop()
    }
}