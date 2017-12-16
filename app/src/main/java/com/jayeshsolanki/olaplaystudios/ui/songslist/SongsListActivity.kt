package com.jayeshsolanki.olaplaystudios.ui.songslist

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.jayeshsolanki.olaplaystudios.OlaPlayStudios
import com.jayeshsolanki.olaplaystudios.R
import com.jayeshsolanki.olaplaystudios.ui.portfolio.PortfolioActivity
import com.jayeshsolanki.olaplaystudios.util.insideTransaction
import kotlinx.android.synthetic.main.activity_songs_list.*
import javax.inject.Inject

class SongsListActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var songsListPresenter: SongsListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_list)

        nav_view.setNavigationItemSelectedListener(this)
        addSongsListFragment()
    }

    private fun addSongsListFragment() {
        val songsListFragment = SongsListFragment.newInstance()
        supportFragmentManager.insideTransaction { add(R.id.container, songsListFragment) }
        DaggerSongsListComponent.builder()
                .songsRepositoryComponent((application as OlaPlayStudios).songsRepositoryComponent)
                .songsListPresenterModule(SongsListPresenterModule(songsListFragment))
                .build()
                .inject(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_home -> {
                // Do nothing
            }
            R.id.nav_portfolio -> {
                val i = Intent(this, PortfolioActivity::class.java)
                startActivity(i)
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}
