package com.geeduke.gadang_gadangan.viewmodels

import androidx.lifecycle.*
import com.geeduke.gadang_gadangan.api.RequestState
import com.geeduke.gadang_gadangan.models.PlantResponse
import com.geeduke.repositories.PlantRepository
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import kotlin.random.Random

class PlantViewModel:ViewModel() {
    private val repo : PlantRepository = PlantRepository()
    private var plantPage = Random.nextInt(1,700)
    private var randomPage = Random.nextInt(1,10000)
    private var searchPage = 1
    private var plantPageResponse: PlantResponse? = null
    private var searchPageResponse: PlantResponse? =null
    private var _pageResponse= MutableLiveData<RequestState<PlantResponse?>>()
    var pageResponse : LiveData<RequestState<PlantResponse?>> = _pageResponse

    private var _searchResponse = MutableLiveData<RequestState<PlantResponse?>>()
    var searchResponse : LiveData<RequestState<PlantResponse?>> = _searchResponse



    fun getPlantList(){
//        plantPageResponse = null
        viewModelScope.launch {
            _pageResponse.postValue(RequestState.Loading)
            val response = repo.getPlantList(plantPage)
            _pageResponse.postValue(handlePlantPageResponse(response))
        }
    }

    private fun handlePlantPageResponse(response: Response<PlantResponse>): RequestState<PlantResponse?> {
        return if (response.isSuccessful){
            response.body()?.let {
                    plantPage++
                if (plantPageResponse == null) plantPageResponse = it else{
                    val oldPlants = plantPageResponse?.data
                    val newPlants = it.data
                    oldPlants?.addAll(newPlants)
                }
            }
            RequestState.Success(plantPageResponse?:response.body())
        }else RequestState.Error(
            try {
                response.errorBody()?.string()?.let {
                    JSONObject(it).get("status_message")
                }
            }catch (e:JSONException){
                e.localizedMessage
            }as String
        )
    }

    fun searchPLant(q:String){
//        searchPageResponse = null
        viewModelScope.launch {
            _searchResponse.postValue(RequestState.Loading)
            val response = repo.plantSearch(q,searchPage)
            _searchResponse.postValue(handlePlantSearchResponse(response))
        }
    }

    private fun handlePlantSearchResponse(response: Response<PlantResponse>):RequestState<PlantResponse?>{
        return if(response.isSuccessful){
            response.body()?.let {
                searchPage++
                if (searchPageResponse == null) searchPageResponse = it else{
                    val oldPlants = searchPageResponse?.data
                    val newPLants = it.data
                    oldPlants?.addAll(newPLants)
                }
            }
            RequestState.Success(searchPageResponse?:response.body())
        }else RequestState.Error(
            try {
                response.errorBody()?.string()?.let {
                    JSONObject(it).get("status_message")
                }
            }catch (e:JSONException){
                e.localizedMessage
            }as String
        )

    }


    fun getPlantsList(): LiveData<RequestState<PlantResponse>> = liveData {
        emit(RequestState.Loading)
        try {
            val response = repo.getPlantList(plantPage)
            val plantResponse = response.body() // Mengambil body respons
            if (response.isSuccessful && plantResponse != null) {
                emit(RequestState.Success(plantResponse))
            } else {
                emit(RequestState.Error("Failed to fetch plant list"))
            }
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string()?.toString() ?: "Unknown error occurred"
            emit(RequestState.Error(errorMessage))
        } catch (e: Exception) {
            emit(RequestState.Error("Unknown error occurred"))
        }
    }

}