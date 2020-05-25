package com.example.gm_challenge.model.data.item

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Track (
	@SerializedName("name") val name : String,
	@SerializedName("duration") val duration : Int,
	@SerializedName("mbid") val mbid : String,
	@SerializedName("url") val url : String,
	@SerializedName("streamable") val streamable : Streamable,
	@SerializedName("artist") val artist : Artist,
	@SerializedName("image") val image : List<Image>,
	@Expose(serialize = false) var isPlaying: Boolean
) : Parcelable