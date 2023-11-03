package com.geeduke.gadang_gadangan.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geeduke.gadang_gadangan.databinding.PlantItemBinding
import com.geeduke.gadang_gadangan.listeners.OnPlantsClickListener
import com.geeduke.gadang_gadangan.models.Plants

class PlantsListAdapter:RecyclerView.Adapter<PlantsListAdapter.ViewHolder>() {
    private val plantList = ArrayList<Plants>()

    lateinit var onPlantsClickListener: OnPlantsClickListener

    fun onPlantsClickListener(onPlantsClickListener: OnPlantsClickListener){
        this.onPlantsClickListener=onPlantsClickListener
    }

    fun setPlants(list: List<Plants>){
        this.plantList.clear()
        this.plantList.addAll(list)
        notifyDataSetChanged()
    }


    private val differBallback = object: DiffUtil.ItemCallback<Plants>(){
        override fun areItemsTheSame(oldItem: Plants, newItem: Plants): Boolean =oldItem == newItem

        override fun areContentsTheSame(oldItem: Plants, newItem: Plants): Boolean = oldItem == newItem

    }
    val differ = AsyncListDiffer(this,differBallback)
    inner class ViewHolder(val binding: PlantItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = PlantItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(differ.currentList[position]){
                binding.apply {
                    tvName.text = commonName
                    tvGenus.text = scientificName
                    Glide.with(itemView).load(imageUrl).into(ivPlants)
                    itemView.setOnClickListener { onPlantsClickListener.onPlantClick(this@with) }
                }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

}