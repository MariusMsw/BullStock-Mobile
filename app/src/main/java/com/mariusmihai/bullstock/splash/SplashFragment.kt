package com.mariusmihai.bullstock.splash

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mariusmihai.bullstock.R
import com.mariusmihai.bullstock.core.BaseFragment
import com.mariusmihai.bullstock.databinding.SplashScreenBinding

class SplashFragment : BaseFragment<SplashScreenBinding>() {

    override val layout: Int
        get() = R.layout.splash_screen

    override val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.apply {
            navigateToLogin = {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }

            navigateToTrading = {
                findNavController().navigate(R.id.action_splashFragment_to_tradingFragment)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.splashScreen.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                viewModel.navigateBasedOnAuth()
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        })
    }
}