package com.mariusmihai.bullstock.profile.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.core.helpers.showAlertDialog
import com.mariusmihai.bullstock.databinding.FragmentHistoryBinding
import kotlinx.coroutines.launch

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    override val layout: Int
        get() = R.layout.fragment_history

    override val viewModel: HistoryFragmentViewModel by viewModels()

    private lateinit var adapter: HistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HistoryAdapter(
            mutableListOf()
        )

        lifecycleScope.launch {
            viewModel.retrieveHistory()
        }

        viewModel.showAlert = { message ->
            context?.showAlertDialog(
                title = "",
                message = message,
                positiveButtonText = "Ok"
            )
        }

        viewModel.historyDto.observe(viewLifecycleOwner, {
            adapter.items.clear()
            adapter.items.addAll(it)
            adapter.notifyDataSetChanged()
        })

        binding.recyclerViewHistory.adapter = adapter

        viewModel.back = {
            findNavController().popBackStack()
        }
    }
}
