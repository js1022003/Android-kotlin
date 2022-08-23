package com.example.viewbindingrv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.viewbindingrv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val adaptor = MainAdaptor(TaskList.taskList)
        binding?.taskRv?.adapter = adaptor
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}