package com.skwen.voicerecord.voicelib.db

import android.annotation.SuppressLint
import android.content.Context
import com.skwen.voicerecord.voicelib.dao.DaoMaster
import com.skwen.voicerecord.voicelib.dao.DaoSession

class DbManager private constructor(context: Context) {


    private val dbName = "skwen_voice.db"

    init {
        mDevOpenHelper = DaoMaster.DevOpenHelper(context, dbName)
        mDaoMaster = daoMaster
        mDaoSession = daoSession
    }

    companion object {
        private var mDbManager: DbManager? = null
        @SuppressLint("StaticFieldLeak")
        private var mDevOpenHelper: DaoMaster.DevOpenHelper? = null
        private var mDaoMaster: DaoMaster? = null
        private var mDaoSession: DaoSession? = null


        fun getInstance(context: Context): DbManager? {
            if (null == mDbManager) {
                synchronized(DbManager::class.java) {
                    if (null == mDbManager) {
                        mDbManager = DbManager(context)
                    }
                }
            }
            return mDbManager
        }

        /**
         * 获取DaoMaster
         *
         * @return
         */
        val daoMaster: DaoMaster?
            get() {
                if (null == mDaoMaster) {
                    synchronized(DbManager::class.java) {
                        if (null == mDaoMaster) {
                            mDaoMaster = DaoMaster(mDevOpenHelper?.writableDb)
                        }
                    }
                }
                return mDaoMaster
            }

        /**
         * 获取DaoSession
         *
         * @return
         */
        val daoSession: DaoSession?
            get() {
                if (null == mDaoSession) {
                    synchronized(DbManager::class.java) {
                        mDaoSession = daoMaster?.newSession()
                    }
                }
                return mDaoSession
            }
    }

}
