package com.skwen.voicerecord.voicelib.service

import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Environment
import android.os.IBinder
import com.skwen.voicerecord.baselib.tools.L

import java.io.File
import java.io.IOException

open class RecordService : Service() {

    private var mRecorder: MediaRecorder? = null
    /**
     * 录音开始时间
     */
    private var startTime: Long = 0
    /**
     * 录音生产的文件路径
     */
    private var voicePath: String? = null

    /**
     * 录音生产的文件名
     */
    private var voiceName: String? = null

    /**
     * 录音时间 从开始录音到停止时的时间
     */
    private var voiceTime: Long = 0

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        L.d("启动服务。。。")
        startRecord()
        return Service.START_STICKY
    }

    override fun onDestroy() {
        if (mRecorder != null) {
            stopVoice()
        }
        super.onDestroy()
    }

    private fun startRecord() {
        createVoiceNameAndPath()
        try {
            mRecorder = MediaRecorder()
            //设置声音来源
            mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            //设置输出格式
            mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            //设置输出路径
            mRecorder!!.setOutputFile(voicePath)
            //设置音频编码
            mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            //设置录制的音频通道数 通常是1(单声道)或2(立体声)
            mRecorder!!.setAudioChannels(1)
            //每秒采样的音频采样率。
            mRecorder!!.setAudioSamplingRate(44100)
            //音频编码比特率(以位/秒为单位)。
            mRecorder!!.setAudioEncodingBitRate(192000)
            //准备
            mRecorder!!.prepare()
            //开始
            mRecorder!!.start()
            //记录开始时间
            startTime = System.currentTimeMillis()
            L.d("开始录音。。。")
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun createVoiceNameAndPath() {
        var voiceFile: File
        var count = 0
        val folder = File(Environment.getExternalStorageDirectory().toString() + "/myPrivateRecord")
        if (!folder.exists()) {
            //folder /SoundRecorder doesn't exist, create the folder
            folder.mkdir()
        }
        do {
            count++
            voiceName = "我的录音_$count.mp4"
            voicePath = Environment.getExternalStorageDirectory().toString() + "/myPrivateRecord/" + voiceName!!
            voiceFile = File(voicePath!!)
        } while (voiceFile.exists() && !voiceFile.isDirectory)
        L.d("创建路劲。。。")
    }


    private fun stopVoice() {
        //停止播放
        mRecorder!!.stop()
        //获取录制时间
        voiceTime = System.currentTimeMillis() - startTime
        //释放资源
        mRecorder!!.release()
        //更新数据库

        //置空参数
        mRecorder = null
        L.d("停止录音。。。")
    }

}
