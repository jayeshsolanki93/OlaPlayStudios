package com.jayeshsolanki.olaplaystudios.ui.portfolio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jayeshsolanki.olaplaystudios.R
import com.jayeshsolanki.olaplaystudios.util.Constants
import kotlinx.android.synthetic.main.activity_portfolio.*

class PortfolioActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio)

        email.setOnClickListener {
            val i = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
            i.putExtra(Intent.EXTRA_SUBJECT, Constants.MAIL_SUBJECT)
            i.putExtra(Intent.EXTRA_TEXT, Constants.MAIL_BODY)
            startActivity(i)
        }

        github.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(Constants.GITHUB)
            startActivity(i)
        }

        stackoverflow.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(Constants.STACKOVERFLOW)
            startActivity(i)
        }

        linkedin.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(Constants.LINKEDIN)
            startActivity(i)
        }

        twitter.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(Constants.TWITTER)
            startActivity(i)
        }
    }

}
