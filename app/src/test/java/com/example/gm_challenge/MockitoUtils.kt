package com.example.gm_challenge

import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

inline fun <T> whenever(methodCall: T) : OngoingStubbing<T> = Mockito.`when`(methodCall)