package com.geeduke.gadang_gadangan.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PlantResponse(

	@field:SerializedName("data")
	val data: MutableList<Plants>,

	@field:SerializedName("meta")
	val meta: Meta? = null,

	@field:SerializedName("links")
	val links: Links? = null
) : Parcelable