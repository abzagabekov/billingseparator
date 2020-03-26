package com.example.billingseparator.bills

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.billingseparator.R
import com.example.billingseparator.database.bills.Bill
import com.example.billingseparator.databinding.ListItemBillBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException


class BillsAdapter(val context: Context, val clickListener: BillsListener) : ListAdapter<Bill, BillsAdapter.ViewHolder>(BillsDiffCallback()) {

    private var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)

        if (tracker!!.isSelected(getItemId(position))) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.binding.root.background = ColorDrawable(context.getColor(R.color.my_app_color_on_surface))
            } else {
                holder.binding.root.background = ColorDrawable(Color.parseColor("#CF6679"))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.binding.root.background = ColorDrawable(context.getColor(R.color.my_app_color_on_background))
            } else {
                holder.binding.root.background = ColorDrawable(Color.parseColor("#0A000000"))
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).billId
    }

    fun setTracker(tracker: SelectionTracker<Long>?) {
        this.tracker = tracker
    }


    class ViewHolder private constructor(val binding: ListItemBillBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBillBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(
            item: Bill,
            clickListener: BillsListener
        ) {
            binding.bill = item
            binding.clickListener = clickListener

            binding.executePendingBindings()
        }

        fun getItemDetails() : ItemDetailsLookup.ItemDetails<Long> = object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getSelectionKey(): Long? = itemId

            override fun getPosition(): Int = adapterPosition

        }

    }
}

class BillsDiffCallback : DiffUtil.ItemCallback<Bill>() {
    override fun areItemsTheSame(oldItem: Bill, newItem: Bill): Boolean {
        return oldItem.billId == newItem.billId
    }

    override fun areContentsTheSame(oldItem: Bill, newItem: Bill): Boolean {
        return oldItem == newItem
    }

}

class BillsListener(val clickListener: (billId: Long) -> Unit) {
    fun onClick(bill: Bill) = clickListener(bill.billId)
}

