package com.skwen.voicerecord.voicelib.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.skwen.voicerecord.voicelib.entity.Voice;

import com.skwen.voicerecord.voicelib.dao.VoiceDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig voiceDaoConfig;

    private final VoiceDao voiceDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        voiceDaoConfig = daoConfigMap.get(VoiceDao.class).clone();
        voiceDaoConfig.initIdentityScope(type);

        voiceDao = new VoiceDao(voiceDaoConfig, this);

        registerDao(Voice.class, voiceDao);
    }
    
    public void clear() {
        voiceDaoConfig.clearIdentityScope();
    }

    public VoiceDao getVoiceDao() {
        return voiceDao;
    }

}
