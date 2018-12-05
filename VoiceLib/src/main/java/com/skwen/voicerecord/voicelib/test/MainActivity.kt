package com.skwen.voicerecord.voicelib.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.skwen.voicerecord.voicelib.R
import com.skwen.voicerecord.voicelib.VoiceListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.container, VoiceListFragment())
        fm.commit()
    }
}
