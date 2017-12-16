package com.jayeshsolanki.olaplaystudios.ui.songslist

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jayeshsolanki.olaplaystudios.OlaPlayStudios
import com.jayeshsolanki.olaplaystudios.R
import com.jayeshsolanki.olaplaystudios.util.insideTransaction
import javax.inject.Inject

class SongsListActivity: AppCompatActivity() {

    @Inject
    lateinit var songsListPresenter: SongsListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_list)

        Log.i("CLASS", "JAYESH KAJOL")
        val songsListFragment = SongsListFragment.newInstance()
        supportFragmentManager.insideTransaction { add(R.id.container, songsListFragment) }
        DaggerSongsListComponent.builder()
                .songsRepositoryComponent((application as OlaPlayStudios).songsRepositoryComponent)
                .songsListPresenterModule(SongsListPresenterModule(songsListFragment))
                .build()
                .inject(this)
    }

}
