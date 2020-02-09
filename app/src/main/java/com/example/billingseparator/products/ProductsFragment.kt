package com.example.billingseparator.products


import android.app.Person
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.setPadding
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.billingseparator.R
import com.example.billingseparator.databinding.ProductsFragmentBinding
import com.example.billingseparator.params.ParamsViewModel
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

        val productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)
        binding.productsViewModel = productsViewModel

        createChips(inflater, container, binding, productsViewModel)

        binding.btnAdd.setOnClickListener {
            if (!binding.etProductName.text.isNullOrEmpty()) {
                if (!binding.etProductPrice.text.isNullOrEmpty()) {
                    val checkedPersonsIds: MutableList<Int> = mutableListOf()
                    for (i in 0 until binding.chipGpRow.childCount) {
                        val chip = binding.chipGpRow.getChildAt(i) as Chip
                        if (chip.isChecked) {
                            checkedPersonsIds.add(chip.id)
                        }
                    }
                    if (!checkedPersonsIds.isNullOrEmpty()) {
                        productsViewModel.onProductAdd(binding.etProductName.text.toString(), binding.etProductPrice.text.toString().toDouble(), checkedPersonsIds.toIntArray())
                    } else {
                        Toast.makeText(context, "No person is checked", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //Toast.makeText(context, "Add ${productsViewModel.products.value?.last()?.productBuyers?.size}", Toast.LENGTH_SHORT).show()
        }

        ProductsViewModel.products.observe(this, Observer {
            if (productsViewModel.eventProductAdd) {
                if (!it.isNullOrEmpty()) {
                    val newRow =
                        inflater.inflate(R.layout.sample_table_row, container, false) as TableRow
                    newRow.id = it.last().productId
                    newRow.tv_product_item.text = it.last().name
                    newRow.tv_price_item.text = it.last().productPrice.toString()
                    binding.tl.addView(newRow)
                    binding.etProductName.text.clear()
                    binding.etProductPrice.text.clear()
                    productsViewModel.doneProductAdd()
                }
            } else if (productsViewModel.eventProductDelete) {
                val removedRow = view?.findViewById<TableRow>(productsViewModel.deletedProduct!!.productId)
                binding.tl.removeView(removedRow)
                productsViewModel.doneProductDelete()
            }
        })

        productsViewModel.eventNavigateToResults.observe(this, Observer {
            if (it) {
                this.findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToResultFragment())
                productsViewModel.doneNavigating()
            }
        })


        return binding.root
    }

    private fun createChips(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: ProductsFragmentBinding,
        productsViewModel: ProductsViewModel
    ) {
        productsViewModel.participants.value?.forEach {
            val newChip = inflater.inflate(R.layout.layout_chip_choice, container, false) as Chip
            newChip.text = it.name
            newChip.id = it.personId
            binding.chipGpRow.addView(newChip)
        }
    }


}
