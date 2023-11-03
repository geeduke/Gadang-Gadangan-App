package com.geeduke.gadang_gadangan.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Meta(

	@field:SerializedName("total")
	val total: Int? = null
) : Parcelable