package com.skwen.voicerecord.voicelib.db

import android.content.Context
import com.skwen.voicerecord.voicelib.dao.DaoMaster
import com.skwen.voicerecord.voicelib.dao.DaoSession

object DbManager {


    private val dbName = "skwen_voice.db"

    private var mDaoSession: DaoSession? = null

    fun getDaoSession(context: Context): DaoSession? {
        if (mDaoSession == null) {
            val devOpenHelper = DaoMaster.DevOpenHelper(context, dbName)
            mDaoSession = getSession(devOpenHelper)
        }
        return mDaoSession
    }

    private fun getSession(devOpenHelper: DaoMaster.DevOpenHelper): DaoSession? {

        return DaoMaster(devOpenHelper.writableDatabase).newSession()

    }

}
