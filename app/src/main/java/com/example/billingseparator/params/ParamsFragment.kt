package com.example.billingseparator.params


import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.billingseparator.R
import com.example.billingseparator.databinding.ParamsFragmentBinding
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.example.billingseparator.database.BillDatabase
import kotlinx.android.synthetic.main.params_fragment.*


class ParamsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: ParamsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.params_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = BillDatabase.getInstance(application).personDatabaseDao
        val arguments = ParamsFragmentArgs.fromBundle(arguments!!)
        val viewModelFactory = ParamsViewModelFactory(dataSource, application, arguments.billId)
        val paramsViewModel = ViewModelProviders.of(this, viewModelFactory).get(ParamsViewModel::class.java)
        binding.paramsViewModel = paramsViewModel
        binding.lifecycleOwner = this


        paramsViewModel.personAddedEvent.observe(this, Observer {
            if (it) {
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(et_name.windowToken, 0)
                paramsViewModel.donePersonAdd()
                binding.etName.text.clear()
            }
        })

        paramsViewModel.navigateToProducts.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(ParamsFragmentDirections.actionParamsFragmentToProductsFragment(paramsViewModel.billId))
                paramsViewModel.doneNavigating()
            }
        })

        return binding.root
    }


}
