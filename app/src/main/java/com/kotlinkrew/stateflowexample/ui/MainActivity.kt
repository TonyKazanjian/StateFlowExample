package com.kotlinkrew.stateflowexample.ui

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
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
import kotlinx.coroutines.flow.collectLatest
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

        edit_text_alphabetic_search.apply {
            setOnFocusChangeListener { _, _ -> showKeyboard()}
            setOnClickListener {
                edit_text_alphabetic_search?.text?.clear()
            }
            addTextChangedListener { editable ->
                if (editable.toString().trim().isNotEmpty()) {
                    viewModel.fetchBreeds(editable?.get(0) ?: "a"[0])
                }
            }
        }

        lifecycleScope.launch {

            // Collect results of StateFlow and update UI

        }
    }

    private fun showKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(main_root.windowToken, 0)
    }

    inner class MainViewModelFactory(private val repository: DogRepository): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(repository) as T
    }
}