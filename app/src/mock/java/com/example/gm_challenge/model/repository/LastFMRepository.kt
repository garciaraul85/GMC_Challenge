package com.example.gm_challenge.model.repository

import com.example.gm_challenge.model.data.element.Tag
import com.example.gm_challenge.model.data.item.Artist
import com.example.gm_challenge.model.data.item.Image
import com.example.gm_challenge.model.data.item.Streamable
import com.example.gm_challenge.model.data.item.Track
import com.example.gm_challenge.model.remote.LastFMRestService
import io.reactivex.Single

open class LastFMRepository(private val lastFMRestService: LastFMRestService): Repository {

    override fun getTopTags(): Single<MutableList<Tag>> {
        return Single.just(mutableListOf(Tag("Pop",1, 1)))
    }

    override fun getTopTracksByTag(tag: Tag): Single<MutableList<Track>> {
        val images= mutableListOf(Image("", ""))

        val artist = Artist("Michael Jackson", "", "")
        val streamable = Streamable(1, 1)
        val track = Track("BillieJean",1, "", "",
            streamable, artist, images)

        val response = mutableListOf(track, track)
        return Single.just(response)
    }

}