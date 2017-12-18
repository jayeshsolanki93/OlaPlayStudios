package com.jayeshsolanki.olaplaystudios.util

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util

object AudioPlayerHelper {


    fun prepareAudioSource(songUrl: String, context: Context): MediaSource {
        val userAgent = Util.getUserAgent(context, Constants.APP_NAME)
        val httpDataSourceFactory = DefaultHttpDataSourceFactory(userAgent, null,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true /* allowCrossProtocolRedirects */
        )
        val dataSourceFactory = DefaultDataSourceFactory(context, null, httpDataSourceFactory)
        val extractorsFactory = DefaultExtractorsFactory()
        return ExtractorMediaSource(Uri.parse(songUrl), dataSourceFactory,
                extractorsFactory, null,  null)
    }

}
