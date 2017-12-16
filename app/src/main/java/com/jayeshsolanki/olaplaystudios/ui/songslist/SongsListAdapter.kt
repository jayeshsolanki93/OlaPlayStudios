package com.jayeshsolanki.olaplaystudios.ui.songslist

import android.preference.PreferenceManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jayeshsolanki.olaplaystudios.R
import com.jayeshsolanki.olaplaystudios.data.model.Song
import com.jayeshsolanki.olaplaystudios.tool.glide.GlideApp
import kotlinx.android.synthetic.main.card_song.view.*

class SongsListAdapter : RecyclerView.Adapter<SongsListAdapter.SongViewHolder>() {

    private var listener: ButtonClickListener? = null

    private var songs: MutableList<Song> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.card_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder?, position: Int) {
        holder?.bindSong(songs[position], listener)
    }

    override fun getItemCount() = songs.size

    fun setAdapterData(songs: MutableList<Song>) {
        this.songs = songs
        notifyDataSetChanged()
    }

    fun setButtonClickListener(listener: ButtonClickListener) {
        this.listener = listener
    }

    interface ButtonClickListener {
        fun playButtonClick(songUrl: String, songName: String)

        fun favButtonClick(song: Song)
    }

    class SongViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        fun bindSong(song: Song, listener: ButtonClickListener?) {
            itemView.txtview_name.text = song.name
            itemView.txtview_artists.text = song.artists
            GlideApp.with(view.context)
                    .load(song.coverImageUrl)
                    .fitCenter()
                    .placeholder(R.color.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(itemView.cover_image)

            val prefs = PreferenceManager.getDefaultSharedPreferences(this.view.context)
            val savedSongs = prefs.getString("SAVED", Gson().toJson(ArrayList<Song>()))

            val savedSongsList =
                    Gson().fromJson<List<Song>>(savedSongs, object: TypeToken<List<Song>>() {}.type).toMutableList()

            var flag = false
            if (savedSongsList.isNotEmpty()) {
                for (savedSong in savedSongsList) {
                    if (savedSong.name.equals(song.name)) {
                        flag = true
                        break
                    }
                }
            }
            itemView.btn_favorite.isChecked = flag

            itemView.btn_favorite.setOnCheckedChangeListener { _, isChecked ->
                Log.i("is fav button checked", isChecked.toString())
                listener?.favButtonClick(song)
            }

            itemView.btn_play.setOnClickListener{ listener?.playButtonClick(song.url, song.name) }
        }

    }

}
