package com.kotlinkrew.stateflowexample.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlinkrew.stateflowexample.R
import com.kotlinkrew.stateflowexample.network.DogRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(DogRepository()) }
    private val rowAdapter = RowAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view_breeds_list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = rowAdapter
        }

        edit_text_alphabetic_search.setOnClickListener {
            it.edit_text_alphabetic_search.text?.clear()
        }
        edit_text_alphabetic_search.addTextChangedListener { editable ->
            if (editable.toString().trim().isNotEmpty()){
                viewModel.fetchBreeds(editable?.get(0) ?: "a"[0])
            }
        }

        lifecycleScope.launch {
            viewModel.mainStateFlow.collect { state ->
                rowAdapter.notifyDataSetChanged()
                rowAdapter.submitList(state.result)
            }
        }
    }

    inner class MainViewModelFactory(private val repository: DogRepository): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(repository) as T
    }
}