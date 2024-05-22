package com.project.nyacawa.presentation.ui.alert_dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.nyacawa.R
import com.project.nyacawa.databinding.DialogFilterBinding
import com.project.nyacawa.domain.adapters.YearsScrollAdapter

class FilterDialog(
): DialogFragment(
) {
    private lateinit var binding: DialogFilterBinding
    private var seasonIndex: Int = -1
    private var yearIndex: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFilterBinding.inflate(inflater, container, false)

        binding.apply.setOnClickListener{
            dialog?.cancel()
        }

        binding.cancel.setOnClickListener{
            //TODO: Filter apply method
            dialog?.cancel()
        }

        binding.winterSeason.setOnClickListener {
            setSeasonColor(1)
        }
        binding.springSeason.setOnClickListener {
            setSeasonColor(2)
        }
        binding.summerSeason.setOnClickListener {
            setSeasonColor(3)
        }
        binding.autumnSeason.setOnClickListener {
            setSeasonColor(4)
        }

        val click: onFilterYearClick = {
            yearIndex = it
        }

        yearList(context, binding.seasonList, click)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private fun setSeasonColor(tempButton: Int){
        seasonIndex = tempButton
        binding.winterSeason.background = getDrawable(requireContext(), R.drawable.years_button_background)
        binding.springSeason.background = getDrawable(requireContext(), R.drawable.years_button_background)
        binding.summerSeason.background = getDrawable(requireContext(), R.drawable.years_button_background)
        binding.autumnSeason.background = getDrawable(requireContext(), R.drawable.years_button_background)

        when (tempButton) {
            1 -> {
                binding.winterSeason.background = getDrawable(requireContext(), R.drawable.years_button_active_background)
            }
            2 -> {
                binding.springSeason.background = getDrawable(requireContext(), R.drawable.years_button_active_background)
            }
            3 -> {
                binding.summerSeason.background = getDrawable(requireContext(), R.drawable.years_button_active_background)
            }
            4 -> {
                binding.autumnSeason.background = getDrawable(requireContext(), R.drawable.years_button_active_background)
            }
        }
    }

    override fun onStart() {
        super.onStart()



        dialog?.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setGravity(Gravity.BOTTOM)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }


    companion object{
        private fun yearList(context: Context?, scrollView: RecyclerView, onFilterYearClick: onFilterYearClick){
            //TODO("The filling should not be here")

            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            scrollView.setLayoutManager(layoutManager)

            scrollView.adapter = YearsScrollAdapter(1980, 2024, onFilterYearClick)
        }
    }
}