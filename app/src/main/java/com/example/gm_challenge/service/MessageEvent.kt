package com.example.gm_challenge.service

data class MessageEvent(var event: Int) {
    companion object {
        const val PLAY = 0
        const val PAUSE = 1
        const val PREVIOUS = 2
        const val NEXT = 3
    }
}