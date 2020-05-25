package com.example.gm_challenge.model.data.item

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artist (
	@SerializedName("name") val name : String,
	@SerializedName("mbid") val mbid : String,
	@SerializedName("url") val url : String
) : Parcelable