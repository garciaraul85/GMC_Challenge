package com.example.gm_challenge.data

import kotlin.random.Random

data class Element(val title: String = "", val elementId: Int = Random.nextInt(1, 10))