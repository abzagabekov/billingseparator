package com.example.billingseparator.result


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.*
import android.widget.TableRow
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.billingseparator.R
import com.example.billingseparator.database.BillDatabase
import com.example.billingseparator.databinding.ResultFragmentBinding
import kotlinx.android.synthetic.main.result_fragment.*
import kotlinx.android.synthetic.main.sample_table_row.view.*
import java.io.File
import java.io.FileOutputStream

class ResultFragment : Fragment() {

    private lateinit var menuItem: MenuItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: ResultFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.result_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val personsDatabase = BillDatabase.getInstance(application).personDatabaseDao
        val arguments = ResultFragmentArgs.fromBundle(arguments!!)
        val viewModelFactory = ResultViewModelFactory(personsDatabase, application, arguments.billId)
        val resultViewModel = ViewModelProviders.of(this, viewModelFactory).get(ResultViewModel::class.java)
        binding.resultViewModel = resultViewModel

        showResults(resultViewModel, inflater, container, binding)

        setHasOptionsMenu(true)
        return binding.root
    }


    private fun showResults(
        resultViewModel: ResultViewModel,
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: ResultFragmentBinding
    ) {
        var summary: Double = 0.0
        resultViewModel.getParticipants().observe(this, Observer {
            it.forEach {
                val newRow =
                    inflater.inflate(R.layout.sample_table_row, container, false) as TableRow
                newRow.tv_product_item.text = it.name
                newRow.tv_price_item.text = String.format("%.1f", it.billSummary)
                summary = summary.plus(it.billSummary)
                binding.tableResult.addView(newRow)
            }
            val newRow =
                inflater.inflate(R.layout.sample_table_row, container, false) as TableRow
            newRow.tv_price_item.text = "${getString(R.string.sumary)} ${summary.toString()}"
            newRow.tv_product_item.text = ""
            binding.tableResult.addView(newRow)

        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_result, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_share -> {
                startActivity(Intent.createChooser(getShareIntent(), "Share via"))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getShareIntent(): Intent {
        table_result.isDrawingCacheEnabled = true
        val bitmap = table_result.drawingCache
        val appDir = File(Environment.getExternalStorageDirectory().absolutePath + "/" + activity?.packageName)
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val cachePath = File(appDir, "image_bill.jpg")
        try {
            cachePath.createNewFile()
            val fos = FileOutputStream(cachePath)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath))
        return shareIntent
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getBitmapFromView(view: View, activity: Activity, callback: (Bitmap) -> Unit) {
        activity.window?.let { window ->
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            view.getLocationInWindow(locationOfViewInWindow)
            try {
                PixelCopy.request(window, Rect(locationOfViewInWindow[0], locationOfViewInWindow[1], locationOfViewInWindow[0] + view.width, locationOfViewInWindow[1] + view.height), bitmap, { copyResult ->
                    if (copyResult == PixelCopy.SUCCESS) {
                        callback(bitmap)
                    }
                    // possible to handle other result codes ...
                }, Handler())
            } catch (e: IllegalArgumentException) {
                // PixelCopy may throw IllegalArgumentException, make sure to handle it
                e.printStackTrace()
            }
        }
    }

    fun loadBitmapFromView(v: View, width: Int, height: Int): Bitmap? {
        val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        v.layout(0, 0, v.layoutParams.width, v.layoutParams.height)
        v.draw(c)
        return b
    }


}
