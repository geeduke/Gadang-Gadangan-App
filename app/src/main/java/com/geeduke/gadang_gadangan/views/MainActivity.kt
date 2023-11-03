package com.geeduke.gadang_gadangan.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.geeduke.gadang_gadangan.R
import com.geeduke.gadang_gadangan.adapters.PlantsListAdapter
import com.geeduke.gadang_gadangan.api.RequestState
import com.geeduke.gadang_gadangan.databinding.ActivityMainBinding
import com.geeduke.gadang_gadangan.listeners.OnPlantsClickListener
import com.geeduke.gadang_gadangan.models.PlantResponse
import com.geeduke.gadang_gadangan.models.Plants
import com.geeduke.gadang_gadangan.viewmodels.PlantViewModel

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var adapter : PlantsListAdapter?=null
    private var layoutManager :LayoutManager?=null
    private val viewModel : PlantViewModel by viewModels()
//    private var _pageResponse= MutableLiveData<RequestState<PlantResponse?>>()
//    var pageResponse : LiveData<RequestState<PlantResponse?>> = _pageResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getPlantList()
        observeAnyChangePlantList()
        setUpRecycleview()

        adapter?.onPlantsClickListener(object : OnPlantsClickListener{
            override fun onPlantClick(plants: Plants) {
                val intent = Intent(this@MainActivity,PlantDetail::class.java)
                intent.putExtra(PlantDetail.plants,plants)
                startActivity(intent)
            }

        })

     binding.searchButton.setOnClickListener {
         val query = binding.search.text.toString()
         when{
             query.isEmpty() -> binding.search.error = "please insert keyword"
             else->{val intent = Intent(this,PlantSearch::class.java)
                 intent.putExtra(PlantSearch.query, query)
                 startActivity(intent)}
         }

     }

    }

    fun observeAnyChangePlantList(){
        viewModel.pageResponse.observe(this){
            if (it != null){
                when(it){
                    is RequestState.Loading -> showLoading()
                    is RequestState.Success -> {
                        hideLoading()
                        it.data?.data?.let { data -> adapter?.differ?.submitList(data.toList().distinct()) }
                    }
                    is RequestState.Error -> {
                        hideLoading()
                        Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun setUpRecycleview(){
        adapter = PlantsListAdapter()
        layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.apply {
            rvPlants.adapter = adapter
            rvPlants.layoutManager = layoutManager
            rvPlants.addOnScrollListener(scrollListener)
        }
    }

    private val scrollListener = object:OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)){
                viewModel.getPlantList()
            }
        }
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