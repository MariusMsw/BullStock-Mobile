package com.mariusmihai.bullstock.profile.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.databinding.FragmentHistoryBinding

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    override val layout: Int
        get() = R.layout.fragment_history

    override val viewModel: HistoryFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}