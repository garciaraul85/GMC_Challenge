package com.example.gm_challenge.model.repository

import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.model.data.item.Track
import io.reactivex.Single

interface Repository {
    fun getTopTags(): Single<MutableList<Tag>>
    fun getTopTracksByTag(tag: Tag): Single<MutableList<Track>>
}