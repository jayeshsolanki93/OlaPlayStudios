package com.jayeshsolanki.olaplaystudios.ui.songslist

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.jayeshsolanki.olaplaystudios.OlaPlayStudios
import com.jayeshsolanki.olaplaystudios.R
import com.jayeshsolanki.olaplaystudios.ui.portfolio.PortfolioActivity
import com.jayeshsolanki.olaplaystudios.util.Constants
import com.jayeshsolanki.olaplaystudios.util.insideTransaction
import kotlinx.android.synthetic.main.activity_songs_list.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class SongsListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        SongsListFragment.BackHandlerInterface {

    @Inject
    lateinit var songsListPresenter: SongsListPresenter

    private var selectedFragment: SongsListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_list)

        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        drawer.addDrawerListener(ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open,R.string.drawer_close))

        nav_view.setNavigationItemSelectedListener(this)

        addSongsListFragment()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                Toast.makeText(this, getString(R.string.nav_home), Toast.LENGTH_SHORT).show()
                if (supportFragmentManager.findFragmentByTag(Constants.ViewType.ALL.value) == null) {
                    addSongsListFragment()
                }
            }
            R.id.nav_playlist -> {
                Toast.makeText(this, getString(R.string.nav_playlist), Toast.LENGTH_SHORT).show()
                if (supportFragmentManager.findFragmentByTag(Constants.ViewType.FAVORITE.value) == null) {
                    addFavoriteFragment()
                }
            }
            R.id.nav_portfolio -> {
                val i = Intent(this, PortfolioActivity::class.java)
                startActivity(i)
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun addSongsListFragment() {
        val songsListFragment = SongsListFragment.newInstance(Constants.ViewType.ALL.value)
        supportFragmentManager.insideTransaction {
            replace(R.id.container, songsListFragment, Constants.ViewType.ALL.value)
        }
        DaggerSongsListComponent.builder()
                .songsRepositoryComponent((application as OlaPlayStudios).songsRepositoryComponent)
                .songsListPresenterModule(SongsListPresenterModule(songsListFragment))
                .build()
                .inject(this)
    }

    private fun addFavoriteFragment() {
        val songsListFragment = SongsListFragment.newInstance(Constants.ViewType.FAVORITE.value)
        supportFragmentManager.insideTransaction {
            replace(R.id.container, songsListFragment, Constants.ViewType.FAVORITE.value)
        }
        DaggerSongsListComponent.builder()
                .songsRepositoryComponent((application as OlaPlayStudios).songsRepositoryComponent)
                .songsListPresenterModule(SongsListPresenterModule(songsListFragment))
                .build()
                .inject(this)
    }

    override fun onBackPressed() {
        selectedFragment?.let {
            if (it.onBackPressed()) {
                super.onBackPressed()
            }
        }
    }

    override fun setSelectedFragment(fragment: SongsListFragment) {
        this.selectedFragment = fragment
    }

}
