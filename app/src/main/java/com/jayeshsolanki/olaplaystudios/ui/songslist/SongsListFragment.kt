package com.jayeshsolanki.olaplaystudios.ui.songslist

import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jayeshsolanki.olaplaystudios.R
import com.jayeshsolanki.olaplaystudios.data.model.Song
import kotlinx.android.synthetic.main.fragment_songs_list.*
import javax.inject.Inject

class SongsListFragment : Fragment(), SongsListContract.View, SongsListAdapter.ButtonClickListener {

    override lateinit var viewType: String

    private lateinit var presenter: SongsListContract.Presenter

    private var adapter = SongsListAdapter()

    private lateinit var exoplayer: ExoPlayer

    companion object {

        private val ARG_FRAGMENT_NAME = "fragmentName"

        @JvmStatic fun newInstance(fragmentName: String): SongsListFragment {
            val fragment = SongsListFragment()
            val args = Bundle()
            args.putString(ARG_FRAGMENT_NAME, fragmentName)
            fragment.arguments = args
            return fragment
        }
    }

    override fun setPresenter(presenter: SongsListContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(ARG_FRAGMENT_NAME)?.let { viewType = it }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_songs_list, container, false)
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
        Toast.makeText(this.context, message,
                Toast.LENGTH_SHORT).show()
    }

    override fun playButtonClick(songUrl: String, songName: String) {
        Toast.makeText(this.context, "Playing $songName", Toast.LENGTH_LONG).show()
        exoplayer.prepare(prepareAudioSource(songUrl))
        exoplayer.playWhenReady = true
    }

    override fun favButtonClick(song: Song) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this.view?.context)
        val savedSongs = prefs.getString("SAVED", Gson().toJson(ArrayList<Song>()))

        val savedSongsList =
                Gson().fromJson<List<Song>>(savedSongs, object: TypeToken<List<Song>>() {}.type).toMutableList()

        if (savedSongsList.isEmpty()) {
            savedSongsList.add(song)
        } else {
            var flag = true
            for (savedSong in savedSongsList) {
                if (savedSong.name.equals(song.name)) {
                    savedSongsList.remove(savedSong)
                    flag = false
                    break
                }
            }
            if (flag) {
                savedSongsList.add(song)
            }
        }
        prefs.edit().putString("SAVED", Gson().toJson(savedSongsList)).apply()

    }

    override fun loadFavoriteSongs() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this.view?.context)
        val savedSongs = prefs.getString("SAVED", Gson().toJson(ArrayList<Song>()))

        val savedSongsList =
                Gson().fromJson<List<Song>>(savedSongs, object: TypeToken<List<Song>>() {}.type).toMutableList()
        list_songs.post {
            if (adapter.itemCount <= 0) {
                adapter.setAdapterData(savedSongsList)
            }
        }
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
