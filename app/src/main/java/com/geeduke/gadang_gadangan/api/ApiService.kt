package com.geeduke.gadang_gadangan.api

import com.geeduke.gadang_gadangan.models.PlantResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/plants")
    suspend fun getPlantList(
        @Query("token") token:String?,
        @Query("page") page:Int?
    ):Response<PlantResponse>

    @GET("v1/plants/search")
    suspend fun plantSearch(
        @Query("token") token: String?,
        @Query("page") page:Int,
        @Query("q") q:String
    ):Response<PlantResponse>
}