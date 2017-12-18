package com.jayeshsolanki.olaplaystudios.ui.portfolio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.jayeshsolanki.olaplaystudios.R
import com.jayeshsolanki.olaplaystudios.util.Constants
import kotlinx.android.synthetic.main.activity_portfolio.*
import kotlinx.android.synthetic.main.toolbar.*

class PortfolioActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio)

        toolbar.title = getString(R.string.activity_portfolio)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        email.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO,
                    Uri.parse(String.format(Constants.MAILTO_URI, Constants.EMAIL)))
            intent.putExtra(Intent.EXTRA_SUBJECT, Constants.MAIL_SUBJECT)
            intent.putExtra(Intent.EXTRA_TEXT, Constants.MAIL_BODY.trimMargin())
            startActivity(intent)
        }

        github.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(Constants.GITHUB)
            startActivity(intent)
        }

        stackoverflow.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(Constants.STACKOVERFLOW)
            startActivity(intent)
        }

        linkedin.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(Constants.LINKEDIN)
            startActivity(intent)
        }

        twitter.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(Constants.TWITTER)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
