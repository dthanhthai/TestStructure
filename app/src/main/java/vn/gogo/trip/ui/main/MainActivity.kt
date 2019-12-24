package vn.gogo.trip.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.koin.android.viewmodel.ext.android.viewModel
import vn.gogo.trip.R
import vn.gogo.trip.extension.observe

class MainActivity : AppCompatActivity() {

    private val viewmodel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViewModel()

        viewmodel.loadGoogleVideoList()
    }

    private fun bindViewModel() {
        observe(viewmodel.googleVideoList) {
            Toast.makeText(this, "Size: ${it!!.googlevideoList.size}", Toast.LENGTH_SHORT).show()
            for (cate in it.googlevideoList) {
                Log.d("TestLog", "Category: ${cate.category}")
                for (video in cate.videos) {
                    Log.d("TestLog", "Video: ${video.title}")
                }
            }
        }
    }
}
