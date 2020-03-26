package com.example.billingseparator.bills


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.example.billingseparator.MyLookup
import com.example.billingseparator.R
import com.example.billingseparator.database.BillDatabase
import com.example.billingseparator.databinding.BillsFragmentBinding


class BillsFragment : Fragment() {

    private var actionMode: ActionMode? = null
    private val actionModeCallback = ActionModeCallback()
    private var tracker: SelectionTracker<Long>? = null
    private var billsToDelete = MutableLiveData<LongArray?>()
    private var allBills: LongArray? = null
    private val ACTION_MODE_BUNDLE_KEY = "action_mode_enabled"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: BillsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.bills_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = BillDatabase.getInstance(application).billDatabaseDao
        val viewModelFactory = BillsViewModelFactory(dataSource, application)
        val billsViewModel = ViewModelProviders.of(this, viewModelFactory).get(BillsViewModel::class.java)

        binding.billsViewModel = billsViewModel
        binding.lifecycleOwner = this

        val adapter = BillsAdapter(context!!, BillsListener {
            billId -> billsViewModel.onBillClicked(billId)
        })

        billsViewModel.navigateToParams.observe(this, Observer {
            it?.let {
                this.findNavController().navigate(BillsFragmentDirections.actionBillsFragmentToParamsFragment(it))
                billsViewModel.onParamsNavigated()
            }
        })

        binding.rvBillsList.adapter = adapter

        billsViewModel.bills.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.submitList(it)
                allBills = it.map { it.billId }.toLongArray()
            }
        })

        tracker = SelectionTracker.Builder<Long>(
            "selection-1",
            binding.rvBillsList,
            StableIdKeyProvider(binding.rvBillsList),
            MyLookup(binding.rvBillsList),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        if (savedInstanceState != null) {
            tracker?.onRestoreInstanceState(savedInstanceState)
            if (savedInstanceState.getBoolean(ACTION_MODE_BUNDLE_KEY)) {
                actionMode = activity?.startActionMode(actionModeCallback)
            }
        }

        tracker?.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                val items = tracker?.selection?.size()
                if (items != null && items > 0) {
                    if (actionMode != null)
                        return
                    actionMode = activity?.startActionMode(actionModeCallback)
                } else if (items == 0) {
                    actionMode?.finish()
                }
            }
        })

        adapter.setTracker(tracker)

        billsToDelete.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                billsViewModel.onBillsDelete(it)
            }
        })

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        tracker?.onSaveInstanceState(outState)
        if (actionMode != null) {
            outState.putBoolean(ACTION_MODE_BUNDLE_KEY, true)
        }
    }


    inner class ActionModeCallback : ActionMode.Callback {

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when(item?.itemId) {
                R.id.action_delete -> {
                    billsToDelete.value = tracker?.selection?.toList()?.toLongArray()
                    actionMode?.finish()
                    true
                }
                R.id.action_select_all -> {
                    allBills?.forEach {
                        tracker?.select(it)
                    }
                    true
                }
                else -> false
            }
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.menu_action_mode, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
            tracker?.clearSelection()
        }

    }

}
