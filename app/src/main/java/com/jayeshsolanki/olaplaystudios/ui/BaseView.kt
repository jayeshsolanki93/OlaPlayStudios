package com.jayeshsolanki.olaplaystudios.ui

interface BaseView<in T> {

    fun setPresenter(presenter: T)
}
