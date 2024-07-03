package com.project.nyacawa.domain.adapters.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.nyacawa.R
import com.project.nyacawa.databinding.FilterButtonBinding
import com.project.nyacawa.presentation.ui.alert_dialog.onFilterYearClick

class YearsScrollAdapter(
    private val firstYear: Int,
    private val lastYear: Int,
    private val onFilterYearClick: onFilterYearClick
) : RecyclerView.Adapter<YearsScrollAdapter.YearViewHolder>() {

    private val yearsList = (firstYear..lastYear).toList()
    private var selectedYear: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FilterButtonBinding.inflate(inflater, parent, false)
        return YearViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YearViewHolder, position: Int) {
        val year = yearsList[position]
        holder.bind(year, onFilterYearClick, year == selectedYear)
        holder.binding.button.setOnClickListener {
            val previousSelectedYear = selectedYear
            selectedYear = year
            notifyItemChanged(position)
            previousSelectedYear?.let {
                val previousIndex = yearsList.indexOf(it)
                if (previousIndex != -1) {
                    notifyItemChanged(previousIndex)
                }
            }
            onFilterYearClick(year)
        }
    }

    override fun getItemCount(): Int {
        return yearsList.size
    }

    class YearViewHolder(val binding: FilterButtonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(year: Int, itemClick: (Int) -> Unit, isSelected: Boolean) {
            binding.button.text = year.toString()
            binding.button.setBackgroundResource(
                if (isSelected) R.drawable.years_button_active_background
                else R.drawable.years_button_background
            )
        }
    }
}
