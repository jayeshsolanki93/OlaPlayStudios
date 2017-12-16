package com.jayeshsolanki.olaplaystudios.ui.songslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jayeshsolanki.olaplaystudios.R
import com.jayeshsolanki.olaplaystudios.data.model.Song
import kotlinx.android.synthetic.main.card_song.view.*

class SongsListAdapter: RecyclerView.Adapter<SongsListAdapter.SongViewHolder>() {

    private var songs: MutableList<Song> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.card_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder?, position: Int) {
        holder?.bindSong(songs[position])
    }

    override fun getItemCount() = songs.size

    fun setAdapterData(songs: MutableList<Song>) {
        this.songs = songs
        notifyDataSetChanged()
    }

    class SongViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        fun bindSong(song: Song) {
            itemView.name.text = song.name
        }

    }

}
