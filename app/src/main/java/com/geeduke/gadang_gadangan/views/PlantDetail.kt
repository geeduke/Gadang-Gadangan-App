package com.geeduke.gadang_gadangan.views

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import com.bumptech.glide.Glide
import com.geeduke.gadang_gadangan.R
import com.geeduke.gadang_gadangan.databinding.ActivityPlantDetailBinding
import com.geeduke.gadang_gadangan.models.Plants

class PlantDetail : AppCompatActivity() {
    private var _binding: ActivityPlantDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_detail)
        _binding = ActivityPlantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.parcelable<Plants>(plants)?.let { plants -> setupData(plants) }
    }

    private fun setupData(plants : Plants){
        with(plants){
            binding.apply {
                Glide.with(this@PlantDetail).load("${plants.imageUrl}").into(ivDetailPlants)
                tvDetailName.text = commonName
                synonyms.text = plants.synonyms.toString()
                tvDetailSienceName.text = scientificName
                tvDetailYear.text=year.toString()
                tvDetailFamily.text=family
                tvDetailGenus.text=genus
                tvDetailAuthor.text=author
            }
        }
    }

    private inline fun<reified T: Parcelable> Intent.parcelable(key:String):T? = when{
        Build.VERSION.SDK_INT >= 33 ->getParcelableExtra(key,T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    companion object{
        const val plants = "plants"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}