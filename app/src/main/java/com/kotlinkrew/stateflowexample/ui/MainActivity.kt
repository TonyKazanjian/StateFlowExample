package com.kotlinkrew.stateflowexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.kotlinkrew.stateflowexample.R
import com.kotlinkrew.stateflowexample.network.DogRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel by viewModels<MainViewModel>()
        viewModel.fetchBreeds(DogRepository())
    }
}