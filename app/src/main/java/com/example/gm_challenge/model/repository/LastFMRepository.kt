package com.example.gm_challenge.model.repository

import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.model.data.item.Track
import com.example.gm_challenge.model.remote.LastFMRestService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class LastFMRepository(private val lastFMRestService: LastFMRestService): Repository {

    override fun getTopTags(): Single<MutableList<Tag>> {
        return lastFMRestService
            .getTopTag()
            .map {
                it.toptags.tag
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTopTracksByTag(tag: Tag): Single<MutableList<Track>> {
        return lastFMRestService
            .getTopTracksByTag(tag.name)
            .map {
                it.tracks.track
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}