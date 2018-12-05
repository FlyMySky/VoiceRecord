package com.skwen.voicerecord.voicelib.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Voice {

    @Id(autoincrement = true)
    private Long id;

    private String voiceName;

    private String voiceTime;

    private String voicePath;

    @Generated(hash = 1251525778)
    public Voice(Long id, String voiceName, String voiceTime, String voicePath) {
        this.id = id;
        this.voiceName = voiceName;
        this.voiceTime = voiceTime;
        this.voicePath = voicePath;
    }

    @Generated(hash = 1158611544)
    public Voice() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoiceName() {
        return this.voiceName;
    }

    public void setVoiceName(String voiceName) {
        this.voiceName = voiceName;
    }

    public String getVoiceTime() {
        return this.voiceTime;
    }

    public void setVoiceTime(String voiceTime) {
        this.voiceTime = voiceTime;
    }

    public String getVoicePath() {
        return this.voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

}
