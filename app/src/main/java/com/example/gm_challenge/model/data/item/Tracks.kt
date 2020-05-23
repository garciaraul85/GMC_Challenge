package com.example.gm_challenge.model.data.item

import com.google.gson.annotations.SerializedName

data class Tracks (
	@SerializedName("track") val track : MutableList<Track>
)