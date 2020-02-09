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

        val chip1 = inflater.inflate(R.layout.layout_chip_choice, container, false) as Chip
        val chip2 = inflater.inflate(R.layout.layout_chip_choice, container, false) as Chip
        val chip3 = inflater.inflate(R.layout.layout_chip_choice, container, false) as Chip
        val chip4 = inflater.inflate(R.layout.layout_chip_choice, container, false) as Chip
        val chip5 = inflater.inflate(R.layout.layout_chip_choice, container, false) as Chip
        val chip6 = inflater.inflate(R.layout.layout_chip_choice, container, false) as Chip
        val chip7 = inflater.inflate(R.layout.layout_chip_choice, container, false) as Chip
        val chip8 = inflater.inflate(R.layout.layout_chip_choice, container, false) as Chip
        val chip9 = inflater.inflate(R.layout.layout_chip_choice, container, false) as Chip
        val chip10 = inflater.inflate(R.layout.layout_chip_choice, container, false) as Chip
        chip1.text = "textChip"
        chip2.text = "textChip"
        chip3.text = "textChip"
        chip4.text = "textChip"
        chip5.text = "textChip"
        chip6.text = "textChip"
        chip7.text = "textChip"
        chip8.text = "textChip"
        chip9.text = "textChip"
        chip10.text = "textChip"

        binding.chipGpRow.addView(chip1)
        binding.chipGpRow.addView(chip2)
        binding.chipGpRow.addView(chip3)
        binding.chipGpRow.addView(chip4)
        binding.chipGpRow.addView(chip5)
        binding.chipGpRow.addView(chip6)
        binding.chipGpRow.addView(chip7)
        binding.chipGpRow.addView(chip8)
        binding.chipGpRow.addView(chip9)
        binding.chipGpRow.addView(chip10)


        val newRow = TableRow(context)
        newRow.layoutParams = binding.trItem2.layoutParams
        newRow.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT)
        newRow.setBackgroundResource(R.drawable.table_row_last_bg)
        newRow.setPadding(5)

        val newTv1 = TextView(context)
        newTv1.text = "new Text"
        newTv1.setTextColor(resources.getColor(R.color.design_default_color_on_secondary))
        newTv1.layoutParams = binding.tvProductItem1.layoutParams
        //newTv1.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT)
        newTv1.setBackgroundResource(R.drawable.table_cell_bg)
        newTv1.setPadding(10,0,10,0)

        val newTv2 = TextView(context)
        newTv2.text = "new Text2"
        newTv2.setTextColor(resources.getColor(R.color.design_default_color_on_secondary))
        newTv2.layoutParams = binding.tvPriceItem2.layoutParams
        //newTv2.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT)
        //newTv2.setBackgroundResource(R.drawable.table_cell_bg)
        newTv2.setPadding(0,0,10,0)

        newRow.addView(newTv1)
        newRow.addView(newTv2)

        val newRow2 = TableRow(context)
        newRow2.layoutParams = binding.trItem2.layoutParams
        newRow2.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT)
        newRow2.setBackgroundResource(R.drawable.table_row_last_bg)
        newRow2.setPadding(5)

        val newTv3 = TextView(context)
        newTv3.text = "new Text sadsad sadsad sad asd asdasdsadasd"
        newTv3.setTextColor(resources.getColor(R.color.design_default_color_on_secondary))
        newTv3.layoutParams = binding.tvProductItem1.layoutParams
        //newTv3.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT)
        newTv3.setBackgroundResource(R.drawable.table_cell_bg)
        newTv3.setPadding(10,0,10,0)

        val newTv4 = TextView(context)
        newTv4.text = "new Text2 "
        newTv4.setTextColor(resources.getColor(R.color.design_default_color_on_secondary))
        newTv4.layoutParams = binding.tvPriceItem2.layoutParams
        //newTv2.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT)
        //newTv4.setBackgroundResource(R.drawable.table_cell_bg)
        newTv4.setPadding(0,0,10,0)

        newRow2.addView(newTv3)
        newRow2.addView(newTv4)


        binding.tl.addView(newRow)
        binding.tl.addView(newRow2)



        return binding.root
    }


}
