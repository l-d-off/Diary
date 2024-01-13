package com.darf.diary.presentation

import android.app.Application
import com.darf.diary.di.DaggerApplicationComponent

class DiaryApp : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}