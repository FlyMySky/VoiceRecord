package com.skwen.voicerecord.voicelib.db

import android.os.Environment

object Const{

    val voicePath = Environment.getExternalStorageDirectory().toString() + "/myPrivateRecord"
    val voiceDictionaryPath = Environment.getExternalStorageDirectory().toString() + "/myPrivateRecord/"

}