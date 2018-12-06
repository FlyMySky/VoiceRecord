package com.skwen.voicerecord.voicelib.entity;

import android.os.Parcel;
import android.os.Parcelable;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


@Entity
public class Voice implements Parcelable {

    @Id(autoincrement = true)
    private Long id;

    private String voiceName;

    private String voiceTime;

    private String voicePath;

    private String recordTime;

    @Generated(hash = 1351532010)
    public Voice(Long id, String voiceName, String voiceTime, String voicePath,
            String recordTime) {
        this.id = id;
        this.voiceName = voiceName;
        this.voiceTime = voiceTime;
        this.voicePath = voicePath;
        this.recordTime = recordTime;
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

    public String getRecordTime() {
        return this.recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.voiceName);
        dest.writeString(this.voiceTime);
        dest.writeString(this.voicePath);
        dest.writeString(this.recordTime);
    }

    protected Voice(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.voiceName = in.readString();
        this.voiceTime = in.readString();
        this.voicePath = in.readString();
        this.recordTime = in.readString();
    }

    public static final Parcelable.Creator<Voice> CREATOR = new Parcelable.Creator<Voice>() {
        @Override
        public Voice createFromParcel(Parcel source) {
            return new Voice(source);
        }

        @Override
        public Voice[] newArray(int size) {
            return new Voice[size];
        }
    };
}
