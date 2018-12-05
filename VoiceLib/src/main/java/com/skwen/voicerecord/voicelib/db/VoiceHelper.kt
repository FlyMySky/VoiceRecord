package com.skwen.voicerecord.voicelib.db

import android.content.Context

class VoiceHelper {

    private object SingletonHolder {
        val holder = VoiceHelper()
    }

    companion object {
        val instance = SingletonHolder.holder
    }

    fun insertData(context: Context){
        DbManager.daoSession
    }

}