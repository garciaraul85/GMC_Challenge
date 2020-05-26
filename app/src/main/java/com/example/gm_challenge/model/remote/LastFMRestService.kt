package com.example.gm_challenge.model.remote

import com.example.gm_challenge.model.data.element.Element
import com.example.gm_challenge.model.data.item.Item
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMRestService {
    @GET("?method=tag.getTopTags")
    fun getTopTag(): Single<Element>

    @GET("?method=tag.gettoptracks")
    fun getTopTracksByTag(@Query("tag") tag: String): Single<Item>
}