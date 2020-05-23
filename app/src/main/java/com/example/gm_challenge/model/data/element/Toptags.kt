package com.example.gm_challenge.model.data.element

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Toptags (
	@SerializedName("tag") val tag : MutableList<Tag>
): Parcelable