package com.example.gm_challenge.model.data.item

import com.google.gson.annotations.SerializedName

data class Image (
	@SerializedName("#text") val text : String,
	@SerializedName("size") val size : String
)