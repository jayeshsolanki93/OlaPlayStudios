package com.jayeshsolanki.olaplaystudios.ui.songslist

import android.content.Context
import android.preference.PreferenceManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jayeshsolanki.olaplaystudios.R
import com.jayeshsolanki.olaplaystudios.data.model.Song
import com.jayeshsolanki.olaplaystudios.tool.glide.GlideApp
import com.jayeshsolanki.olaplaystudios.util.Constants
import kotlinx.android.synthetic.main.card_song.view.*

class SongsListAdapter : RecyclerView.Adapter<SongsListAdapter.SongViewHolder>(), Filterable {

    private lateinit var context: Context

    private var listener: ButtonClickListener? = null

    private var songs: MutableList<Song> = ArrayList()

    private var filteredSongs: MutableList<Song> = ArrayList()

    private var selectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.card_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = filteredSongs[position]

        // Load text data.
        holder.name.text = song.name
        holder.artist.text = song.artists

        // Toggle Play/Pause button.
        if (position == selectedPosition) {
            holder.btnPlayStop.setImageResource(R.drawable.ic_stop)
        } else {
            holder.btnPlayStop.setImageResource(R.drawable.ic_play)
        }

        // Load image.
        GlideApp.with(context)
                .load(song.coverImageUrl)
                .fitCenter()
                .placeholder(R.color.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.coverImage)

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val savedSongs = prefs.getString(Constants.PREFS_KEY_SAVED_PLAYLIST,
                Gson().toJson(ArrayList<Song>()))
        val savedSongsList = Gson().fromJson<List<Song>>(savedSongs,
                object: TypeToken<List<Song>>() {}.type).toMutableList()
        // Mark the item if its in the user's playlist.
        val isInPlaylist = savedSongsList.contains(song)
        holder.btnFavorite.isChecked = isInPlaylist

        // Add click listeners.
        holder.btnFavorite.setOnClickListener { listener?.favButtonClick(song) }

        holder.btnPlayStop.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            if (selectedPosition == adapterPosition) {
                listener?.stopAudio()
                selectedPosition = -1
            } else {
                selectedPosition = adapterPosition
                listener?.stopAudio()
                listener?.playButtonClick(song)
            }
            notifyDataSetChanged()
        }

        holder.btnDownload.setOnClickListener { listener?.downloadClick() }
    }

    override fun getItemCount() = filteredSongs.size

    fun setAdapterData(songs: MutableList<Song>) {
        this.songs = songs
        this.filteredSongs = songs
        notifyDataSetChanged()
    }

    fun setButtonClickListener(listener: ButtonClickListener) {
        this.listener = listener
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                filteredSongs = if (charString.isEmpty()) songs
                else songs.filter { it.name.toLowerCase().contains(charString.toLowerCase()) }
                        .toMutableList()

                val filterResults = FilterResults()
                filterResults.values = filteredSongs
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                if (filterResults.values != null) {
                    val temp = filterResults.values as ArrayList<*>
                    if (temp.isNotEmpty() && temp[0] is Song) {
                        filteredSongs = temp as ArrayList<Song>
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    interface ButtonClickListener {
        fun playButtonClick(song: Song)

        fun favButtonClick(song: Song)

        fun downloadClick()

        fun stopAudio()
    }

    class SongViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.txtview_name
        val coverImage: ImageView = view.cover_image
        val artist: TextView = view.txtview_artists
        val btnFavorite: ToggleButton = view.btn_favorite
        val btnPlayStop: ImageButton = view.btn_play_stop
        val btnDownload: ImageButton = view.btn_download
    }

}
