package com.example.billingseparator.products


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.example.billingseparator.R
import com.example.billingseparator.databinding.ProductsFragmentBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.products_fragment.*
import kotlinx.android.synthetic.main.sample_table_row.view.*

/**
 * A simple [Fragment] subclass.
 */
class ProductsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: ProductsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.products_fragment, container, false)
        val productsViewModel = ProductsViewModel()
        binding.productsViewModel = productsViewModel

        val arguments =ProductsFragmentArgs.fromBundle(arguments!!)


        // add new chip item
        val chip1 = inflater.inflate(R.layout.layout_chip_choice, container, false) as Chip
        chip1.text = "textChip"
        binding.chipGpRow.addView(chip1)


        // add new table row item
        val newRow = inflater.inflate(R.layout.sample_table_row, container, false) as TableRow
        newRow.tv_product_item.text = "salaaaaaaaa"
        newRow.tv_price_item.text = "asdsadasda"
        binding.tl.addView(newRow)


        return binding.root
    }


}
