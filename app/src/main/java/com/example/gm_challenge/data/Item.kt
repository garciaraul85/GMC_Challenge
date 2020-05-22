package com.example.gm_challenge.data

import kotlin.random.Random

data class Item(val title: String = "", val elementId: Int = Random.nextInt(0, 10))