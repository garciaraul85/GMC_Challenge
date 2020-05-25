package com.example.gm_challenge.model.data.item

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Streamable (
	@SerializedName("#text") val text : Int,
	@SerializedName("fulltrack") val fulltrack : Int
) : Parcelable