package com.example.moviefindefapd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.moviefindefapd.databinding.ActivityMainBinding
import com.example.moviefindefapd.ui.view_models.SharedViewModel

class MainActivity : AppCompatActivity() {
    var bindingclass: ActivityMainBinding? = null
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingclass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingclass!!.root)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}