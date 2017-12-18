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

}
