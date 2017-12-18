package com.jayeshsolanki.olaplaystudios.ui.songslist

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jayeshsolanki.olaplaystudios.R
import com.jayeshsolanki.olaplaystudios.data.model.Song
import com.jayeshsolanki.olaplaystudios.util.AudioPlayerHelper
import com.jayeshsolanki.olaplaystudios.util.Constants
import kotlinx.android.synthetic.main.fragment_songs_list.*

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

    override fun showError(errorType: Int) {
        val message = when (errorType) {
            1 -> getString(R.string.error_no_songs)
            2 -> getString(R.string.network_error)
            else -> getString(R.string.miserable_failure)
        }
        Toast.makeText(this.context, message,
                Toast.LENGTH_SHORT).show()
    }

    override fun playButtonClick(song: Song) {
        Toast.makeText(this.context, "Playing ${song.name}", Toast.LENGTH_LONG).show()
        exoplayer.prepare(AudioPlayerHelper.prepareAudioSource(song.url, this.context))
        exoplayer.playWhenReady = true
    }

    override fun stopAudio() {
        exoplayer.stop()
    }

    override fun favButtonClick(song: Song) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this.view?.context)
        val savedSongs = prefs.getString(Constants.PREFS_KEY_SAVED_PLAYLIST,
                Gson().toJson(ArrayList<Song>()))

        val savedSongsList = Gson().fromJson<List<Song>>(savedSongs,
                object: TypeToken<List<Song>>() {}.type).toMutableList()

        if (savedSongsList.isEmpty()) {
            savedSongsList.add(song)
        } else {
            var isInList = false
            for (savedSong in savedSongsList) {
                if (savedSong == song) {
                    savedSongsList.remove(savedSong)
                    isInList = true
                    break
                }
            }
            if (!isInList) {
                savedSongsList.add(song)
            }
        }
        prefs.edit()
                .putString(Constants.PREFS_KEY_SAVED_PLAYLIST, Gson().toJson(savedSongsList))
                .apply()

        Toast.makeText(this.context, getString(R.string.added_to_playlist), Toast.LENGTH_SHORT)
                .show()
    }

    override fun downloadClick() {
        Toast.makeText(this.context, "Sorry! This does not work.", Toast.LENGTH_SHORT).show()
    }

    override fun loadFavoriteSongs() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this.view?.context)
        val savedSongs = prefs.getString(Constants.PREFS_KEY_SAVED_PLAYLIST,
                Gson().toJson(ArrayList<Song>()))

        val savedSongsList = Gson().fromJson<List<Song>>(savedSongs,
                object: TypeToken<List<Song>>() {}.type).toMutableList()
        list_songs.post {
            if (adapter.itemCount <= 0) {
                adapter.setAdapterData(savedSongsList)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        exoplayer.release()
        presenter.unSubscribe()
    }

}
