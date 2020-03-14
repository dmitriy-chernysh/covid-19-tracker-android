package com.mobiledevpro.app.ui.mainscreen.view

import android.os.Bundle
import android.view.View
import com.mobiledevpro.app.R
import com.mobiledevpro.app.databinding.FragmentTotalBinding
import com.mobiledevpro.app.ui.mainscreen.viewmodel.TotalViewModel
import com.mobiledevpro.commons.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Main fragment for main activity
 *
 *
 * Created by Dmitriy Chernysh
 *
 * http://androiddev.pro/
 *
 * #MobileDevPro
 */

class TotalFragment : BaseFragment() {

    private val viewModel: TotalViewModel by viewModel()

    override fun getLayoutResId() = R.layout.fragment_total

    override fun getAppBarTitle() = R.string.app_name_main

    override fun getHomeAsUpIndicatorIcon() = R.drawable.ic_close_24dp

    override fun populateView(view: View, savedInstanceState: Bundle?): View {
        //databinding
        val binding = FragmentTotalBinding.bind(view)
                .apply {
                    totalViewModel = viewModel
                }
        binding.lifecycleOwner = viewLifecycleOwner

        observeEvents(view)
        return binding.root
    }

    override fun initPresenters() {
        //add lifecycle observer to viewmodel
        lifecycle.addObserver(viewModel)
    }

    private fun observeEvents(view: View) {
        //do something
    }
}
