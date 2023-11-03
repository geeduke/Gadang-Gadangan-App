package com.geeduke.gadang_gadangan.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Links(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("last")
	val last: String? = null,

	@field:SerializedName("self")
	val self: String? = null,

	@field:SerializedName("first")
	val first: String? = null,

	@field:SerializedName("genus")
	val genus: String? = null,

	@field:SerializedName("plant")
	val plant: String? = null
) : Parcelable