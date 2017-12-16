package com.jayeshsolanki.olaplaystudios.ui.songslist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jayeshsolanki.olaplaystudios.R
import com.jayeshsolanki.olaplaystudios.data.model.Song
import kotlinx.android.synthetic.main.fragment_songs_list.*

class SongsListFragment : Fragment(), SongsListContract.View {

    private lateinit var presenter: SongsListContract.Presenter

    private var adapter = SongsListAdapter()

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
        list_songs.adapter = adapter
    }

    private fun setupSwipeRefreshListener() {
        swiperefresh.setOnRefreshListener { presenter.loadSongsList() }
    }

    override fun onStart() {
        super.onStart()
        presenter.subscribe()
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

}
