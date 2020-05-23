package com.example.gm_challenge.model.data.element

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag (
	@SerializedName("name") val name : String,
	@SerializedName("count") val count : Int,
	@SerializedName("reach") val reach : Int
): Parcelable