package com.example.gm_challenge.model.data.element

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Element(@SerializedName("toptags") val toptags : Toptags): Parcelable