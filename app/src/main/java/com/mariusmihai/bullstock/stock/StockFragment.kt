package com.mariusmihai.bullstock.stock

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.core.helpers.printMessage
import com.mariusmihai.bullstock.core.helpers.showAlertDialog
import com.mariusmihai.bullstock.databinding.StockScreenBinding

class StockFragment : BaseFragment<StockScreenBinding>() {

    //Here I have the stock that was clicked with StockMostImportantDataDto details in args.stockDetails
    private val args: StockFragmentArgs by navArgs()

    override val layout: Int
        get() = R.layout.stock_screen

    override val viewModel: StockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigateToTrading = {
            findNavController().popBackStack()
        }

        viewModel.stock = args.stockDetails

        viewModel.retrieveStockData()

        viewModel.showAlert = { message ->
            context?.showAlertDialog(
                title = "",
                message = message,
                positiveButtonText = "Ok"
            )
        }


    }
}