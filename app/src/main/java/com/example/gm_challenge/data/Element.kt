package com.example.gm_challenge.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

@Parcelize
data class Element(val title: String = "", val elementId: Int = Random.nextInt(1, 10)): Parcelable