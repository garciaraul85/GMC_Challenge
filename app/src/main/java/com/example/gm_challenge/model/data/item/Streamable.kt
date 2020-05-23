package com.example.gm_challenge.model.data.item

import com.google.gson.annotations.SerializedName

data class Streamable (
	@SerializedName("#text") val text : Int,
	@SerializedName("fulltrack") val fulltrack : Int
)