package com.skwen.voicerecord.baselib

import android.app.Application
import android.content.Context

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        var context: Context? = null
            private set
    }
}