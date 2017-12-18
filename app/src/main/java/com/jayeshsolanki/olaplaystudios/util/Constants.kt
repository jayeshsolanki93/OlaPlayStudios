package com.jayeshsolanki.olaplaystudios.util

object Constants {

    const val APP_NAME = "OlaPlayStudios"

    const val API_URL = "http://starlord.hackerearth.com"

    const val PREFS_KEY_SAVED_PLAYLIST = "SAVED_PLAYLIST"

    const val MAILTO_URI = "mailto:%s"

    const val EMAIL = "jayeshsolanki93@gmail.com"

    const val GITHUB = "https://github.com/jayeshsolanki93"

    const val LINKEDIN = "https://linkedin.com/in/jayeshsolanki93"

    const val TWITTER = "https://twitter.com/jayeshsolanki93"

    const val STACKOVERFLOW = "https://stackoverflow.com/users/2686502/jayeshsolanki93"

    const val MAIL_SUBJECT = "We want you!"

    const val MAIL_BODY = """
    |Hey!
    |Book your tickets to Bangalore. Ola wants you! ヽ(ヅ)ノ
    """

    enum class ViewType(val value: String) {
        ALL("ALL"),
        FAVORITE("FAVORITE")
    }

}
