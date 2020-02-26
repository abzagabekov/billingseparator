package com.example.billingseparator.bills

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.billingseparator.R
import com.example.billingseparator.database.bills.Bill
import kotlinx.android.synthetic.main.list_item_bill.view.*
import java.text.SimpleDateFormat

class BillsAdapter : RecyclerView.Adapter<BillsAdapter.ViewHolder>() {

    var data = listOf<Bill>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_bill, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.billName.text = "${item.billName} #${item.billId}"
        val format = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        holder.billDate.text = format.format(item.billDate)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val billName: TextView = itemView.findViewById(R.id.tv_item_bill_name)
        val billDate: TextView = itemView.findViewById(R.id.tv_item_bill_date)
    }
}