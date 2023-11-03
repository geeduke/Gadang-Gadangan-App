package com.geeduke.gadang_gadangan.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geeduke.gadang_gadangan.adapters.PlantsListAdapter
import com.geeduke.gadang_gadangan.api.RequestState
import com.geeduke.gadang_gadangan.databinding.ActivityPlantSearchBinding
import com.geeduke.gadang_gadangan.listeners.OnPlantsClickListener
import com.geeduke.gadang_gadangan.models.Plants
import com.geeduke.gadang_gadangan.viewmodels.PlantViewModel
import retrofit2.http.GET

class PlantSearch : AppCompatActivity() {
    private var _binding:ActivityPlantSearchBinding?=null
    private val binding get() =_binding!!
    private var adapter : PlantsListAdapter?=null
    private var layoutManager : RecyclerView.LayoutManager?=null
    private val viewModel:PlantViewModel by viewModels()
    private var isSearchAgain = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlantSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.search.setText(intent.getStringExtra(query))
        if (!isSearchAgain) viewModel.searchPLant(binding.search.text.toString())

        setUpRecycleView()
        observeAnyChangePlantSearch()

        adapter?.onPlantsClickListener(object : OnPlantsClickListener {
            override fun onPlantClick(plants: Plants) {
                val intent = Intent(this@PlantSearch,PlantDetail::class.java)
                intent.putExtra(PlantDetail.plants,plants)
                startActivity(intent)
            }

        })

        binding.searchButton.setOnClickListener {
            val query = binding.search.text.toString()
            when {
                query.isEmpty() -> binding.search.error = "please inser a keyword dude"
                else ->{
                    isSearchAgain = true
                    clearSearchResults()
                    viewModel.searchPLant(query)

                }

            }

        }

    }

    private fun clearSearchResults() {
        adapter?.differ?.submitList(emptyList())
        adapter?.notifyDataSetChanged()
    }

    fun observeAnyChangePlantSearch() {
        viewModel.searchResponse.observe(this) {
            if (it != null) {
                when (it) {
                    is RequestState.Loading -> showLoading()
                    is RequestState.Success -> {
                        hideLoading()
                        it.data?.data?.let { data -> adapter?.differ?.submitList(data.toList().distinct())
                        }
                    }
                    is RequestState.Error -> {
                        hideLoading()
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }



    private fun setUpRecycleView(){
        adapter = PlantsListAdapter()
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.apply {
            rvPlants.adapter = adapter
            rvPlants.layoutManager = layoutManager
            rvPlants.addOnScrollListener(scrollListener)
        }
    }

    private val scrollListener = object: RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)){
                viewModel.searchPLant(binding.search.text.toString())
            }
        }
    }

    companion object{
        const val query = "q"
    }

    private fun showLoading(){
        binding.loading.show()
    }
    private fun hideLoading(){
        binding.loading.hide()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}