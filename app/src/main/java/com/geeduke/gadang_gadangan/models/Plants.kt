package com.geeduke.gadang_gadangan.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Plants(

	@field:SerializedName("family_common_name")
	val familyCommonName: String? = null,

	@field:SerializedName("year")
	val year: Int? = 0,

	@field:SerializedName("genus_id")
	val genusId: Int? = 0,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("synonyms")
	val synonyms: List<String?>? = null,

	@field:SerializedName("scientific_name")
	val scientificName: String? = null,

	@field:SerializedName("bibliography")
	val bibliography: String? = null,

	@field:SerializedName("genus")
	val genus: String? = null,

	@field:SerializedName("rank")
	val rank: String? = null,

	@field:SerializedName("links")
	val links: Links? = null,

	@field:SerializedName("id")
	val id: Int? = 0,

	@field:SerializedName("common_name")
	val commonName: String? = null,

	@field:SerializedName("family")
	val family: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable