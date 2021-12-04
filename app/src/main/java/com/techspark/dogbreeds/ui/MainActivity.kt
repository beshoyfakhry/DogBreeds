package com.techspark.dogbreeds.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techspark.dogbreeds.R
import com.techspark.dogbreeds.data.remote.items.DogBreedsItems
import com.techspark.dogbreeds.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: DogBreedViewModel

    @Inject
    lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(DogBreedViewModel::class.java)

        setupSpinnerClickListener()
        setupRecyclerView()
        setListeners()
       setScrollListener()
    }

    private fun setListeners() {
        viewModel.dogBreeds.observe(this, Observer {

            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.INVISIBLE
                    val dogBreedsList = it.data!!.toMutableList()
                    val adapter =
                        ArrayAdapter(this, R.layout.item_dog_breed, dogBreedsList)
                    sp_breeds.adapter = adapter
                }
                Status.ERROR -> {
                    Toast.makeText(
                        this,
                        it.message ?: "An unknown error occurred",
                        Toast.LENGTH_LONG
                    ).show()

                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
            }

        })


        viewModel.breedImages.observe(this, Observer {

            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.INVISIBLE
                    val urls = it.data?.map { imageResult -> imageResult.ImageUrl!! }
                    imageAdapter.images = urls ?: listOf()
                }

                Status.ERROR -> {
                    Toast.makeText(
                        this,
                        it.message ?: "An unknown error occurred",
                        Toast.LENGTH_LONG
                    ).show()

                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
            }


        })
    }

    private fun setupSpinnerClickListener() {
        sp_breeds.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                if (position > 0) {
                    viewModel.requestPage = 0

                    viewModel.selectedBreadId =
                        (parent.adapter.getItem(position) as DogBreedsItems).id.toString()

                    viewModel.listImagesForSelectedDogBreed()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

    }

    private fun setupRecyclerView() {
        rv_result.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 3)
        }
    }

    private fun setScrollListener() {
        rv_result.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.listImagesForSelectedDogBreed()
                }
            }
        })
    }


}
