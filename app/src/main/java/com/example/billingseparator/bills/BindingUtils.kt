package com.example.billingseparator.bills

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.billingseparator.database.bills.Bill
import java.text.SimpleDateFormat

@BindingAdapter("billNameString")
fun TextView.setBillsName(item: Bill?) {
    item?.let {
        text = "${item.billName} #${item.billId}"
    }
}

@BindingAdapter("billDateFormatted")
fun TextView.setBillsDate(item: Bill?) {
    item?.let {
        val format = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        text = format.format(item.billDate)
    }
}