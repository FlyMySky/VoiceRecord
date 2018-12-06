package com.skwen.voicerecord.voicelib.db

import com.skwen.voicerecord.baselib.BaseApp
import com.skwen.voicerecord.voicelib.dao.VoiceDao
import com.skwen.voicerecord.voicelib.entity.Voice
import com.skwen.voicerecord.voicelib.holder.VoiceDataCallBack

class VoiceHelper {

    private lateinit var callBack: VoiceDataCallBack

    private object SingletonHolder {
        val holder = VoiceHelper()
    }

    companion object {
        val instance = SingletonHolder.holder
    }

    fun registerCallBack(callBack: VoiceDataCallBack) {
        this.callBack = callBack
    }

    fun insertData(entity: Voice) {
        DbManager.getDaoSession(BaseApp.context!!)?.voiceDao?.insertOrReplace(entity)
        callBack.onInsertData()
    }

    fun deleteData(entity: Voice) {
        DbManager.getDaoSession(BaseApp.context!!)?.voiceDao?.delete(entity)
    }

    fun deleteDataByKey(key: Long) {
        DbManager.getDaoSession(BaseApp.context!!)?.voiceDao?.deleteByKey(key)
    }

    fun deleteAll() {
        DbManager.getDaoSession(BaseApp.context!!)?.voiceDao?.deleteAll()
    }

    fun queryDataByKey(key: Long): Voice? {
        return DbManager.getDaoSession(BaseApp.context!!)?.voiceDao?.loadByRowId(key)
    }

    fun queryDataByRule(name: String): MutableList<Voice>? {
        return DbManager.getDaoSession(BaseApp.context!!)?.voiceDao?.queryBuilder()
            ?.where(VoiceDao.Properties.VoiceName.eq(name))?.list()
    }

    fun queryAll(): MutableList<Voice>? {
        return DbManager.getDaoSession(BaseApp.context!!)?.voiceDao?.loadAll()
    }

    fun updateData(entity: Voice) {
        DbManager.getDaoSession(BaseApp.context!!)?.voiceDao?.update(entity)
        callBack.onEditData()
    }

    fun dataCount(): Long {
        return DbManager.getDaoSession(BaseApp.context!!)?.voiceDao?.count()!!
    }

}