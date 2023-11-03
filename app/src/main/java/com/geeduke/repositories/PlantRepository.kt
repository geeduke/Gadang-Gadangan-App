package com.geeduke.repositories

import com.geeduke.gadang_gadangan.BuildConfig
import com.geeduke.gadang_gadangan.api.ApiConfig

class PlantRepository {
    private val client = ApiConfig.getApiService()

    suspend fun getPlantList(page:Int) = client.getPlantList(BuildConfig.TOKEN,page)
    suspend fun plantSearch(q: String,page:Int) = client.plantSearch(BuildConfig.TOKEN,page,q)
}