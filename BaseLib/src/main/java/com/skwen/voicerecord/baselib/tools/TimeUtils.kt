package com.skwen.voicerecord.baselib.tools

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object TimeUtils {


    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return simpleDateFormat.format(Date())
    }

    fun getRecordTimeByMill(longTime: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(longTime)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(longTime) - TimeUnit.HOURS.toMinutes(hours)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(longTime) - TimeUnit.MINUTES.toSeconds(minutes)
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

}