package com.skwen.voicerecord.baselib.tools

import android.util.Log

object L {
    private const val TAG = "VoiceRecord"


    fun i(msg: String) {
        Log.i(TAG, msg)
    }

    fun d(msg: String) {
        Log.d(TAG, msg)
    }

    fun e(msg: String) {
        Log.e(TAG, msg)
    }


}