package com.example.gm_challenge.service

data class MessageEvent(var event: Int) {
    companion object {
        const val PREVIOUS = 0
        const val NEXT = 1
    }
}