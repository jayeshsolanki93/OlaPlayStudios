package com.jayeshsolanki.olaplaystudios.ui.songslist

import android.content.pm.ApplicationInfo
import android.media.AudioTrack
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jayeshsolanki.olaplaystudios.R
import com.jayeshsolanki.olaplaystudios.data.model.Song
import kotlinx.android.synthetic.main.fragment_songs_list.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.LoadControl
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util


class SongsListFragment : Fragment(), SongsListContract.View, SongsListAdapter.ButtonClickListener {

    private lateinit var presenter: SongsListContract.Presenter

    private var adapter = SongsListAdapter()

    private lateinit var exoplayer: ExoPlayer

    companion object {

        @JvmStatic fun newInstance(): SongsListFragment {
            val fragment = SongsListFragment()
            // Can pass some args.
            return fragment
        }
    }

    override fun setPresenter(presenter: SongsListContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_songs_list, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwipeRefreshListener()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        list_songs.layoutManager = layoutManager
        adapter.setButtonClickListener(this)
        list_songs.adapter = adapter
    }

    private fun setupSwipeRefreshListener() {
        swiperefresh.setOnRefreshListener { presenter.loadSongsList() }
    }

    override fun onStart() {
        super.onStart()
        presenter.subscribe()
        exoplayer = ExoPlayerFactory.newSimpleInstance(this.context, DefaultTrackSelector())
    }

    override fun showSongsList(songs: List<Song>) {
        if (swiperefresh.isRefreshing) swiperefresh.isRefreshing = false
        list_songs.post {
            if (adapter.itemCount <= 0) {
                adapter.setAdapterData(songs.toMutableList())
            }
        }
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun playButtonClick(songUrl: String, songName: String) {
        Toast.makeText(this.context, "Playing $songName", Toast.LENGTH_LONG).show()
        exoplayer.prepare(prepareAudioSource(songUrl))
        exoplayer.playWhenReady = true
    }

    private fun prepareAudioSource(songUrl: String): MediaSource {
        val userAgent = Util.getUserAgent(context, "OlaPlayStudio")
        val httpDataSourceFactory = DefaultHttpDataSourceFactory(userAgent, null,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true /* allowCrossProtocolRedirects */
        )
        val dataSourceFactory = DefaultDataSourceFactory(this.context, null, httpDataSourceFactory)
        val extractorsFactory = DefaultExtractorsFactory()
        return ExtractorMediaSource(Uri.parse(songUrl), dataSourceFactory,
                extractorsFactory, null,  null)
    }

    override fun onStop() {
        super.onStop()
        exoplayer.release()
        presenter.unSubscribe()
    }
}
