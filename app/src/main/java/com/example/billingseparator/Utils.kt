package com.example.billingseparator

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.room.TypeConverter
import com.example.billingseparator.database.persons.Person
import java.text.SimpleDateFormat
import java.util.stream.Collectors

val chipsIdsPrefix = 0
val tableRowsIdsPrefix = 100000

fun formatPersons(participants: List<Person>): Spanned {
    val sb = StringBuilder()
    sb.apply {
        participants.forEach {
            append(it.name)
            append("<br>")
        }
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}


@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(systemTime).toString()
}

fun personsToMap(participants: List<Person>): Map<Long, String> {
    val res = mutableMapOf<Long, String>()
    participants.forEach{
        res[it.personId] = it.name
    }
    return res
}

