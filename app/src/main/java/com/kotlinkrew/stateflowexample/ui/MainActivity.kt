package com.kotlinkrew.stateflowexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlinkrew.stateflowexample.R
import com.kotlinkrew.stateflowexample.network.DogRepository

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(DogRepository()) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.fetchBreeds()
    }

    inner class MainViewModelFactory(private val repository: DogRepository): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(repository) as T
    }
}