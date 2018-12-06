package com.skwen.voicerecord.voicelib.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SeekBar
import com.skwen.voicerecord.baselib.tools.TimeUtils
import com.skwen.voicerecord.voicelib.R
import com.skwen.voicerecord.voicelib.entity.Voice
import kotlinx.android.synthetic.main.play_voice_fragment.*


class PlayVoiceFragment : DialogFragment() {

    private lateinit var voice: Voice
    private var isPlay: Boolean = false
    private var mPlayer: MediaPlayer? = null
    private var mHandler: Handler = Handler()

    companion object {
        const val TAG = "playVoice"
        fun instance(voice: Voice): PlayVoiceFragment {
            val fragment = PlayVoiceFragment()
            val bundle = Bundle()
            bundle.putParcelable(TAG, voice)
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun initViews() {
        activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        fileName.text = voice.voiceName
        fileLengthText.text = voice.voiceTime
        filePlay.setOnClickListener {
            run {
                playFile(isPlay)
                isPlay = !isPlay
            }
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (mPlayer != null && fromUser) {
                    mPlayer?.seekTo(progress)
                    mHandler.removeCallbacks(mRunnable)
                    currentProgressText.text = TimeUtils.getRecordTimeByMill(mPlayer?.currentPosition!!.toLong())
                    updateSeekBar()
                } else if (mPlayer == null && fromUser) {
                    preparePlayer(progress)
                    updateSeekBar()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (mPlayer != null) {
                    mHandler.removeCallbacks(mRunnable)
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (mPlayer != null) {
                    mHandler.removeCallbacks(mRunnable)
                    mPlayer?.seekTo(seekBar?.progress!!)
                    currentProgressText.text = TimeUtils.getRecordTimeByMill(seekBar?.progress!!.toLong())
                    updateSeekBar()
                }
            }

        })


    }

    private fun preparePlayer(progress: Int) {
        mPlayer = MediaPlayer()
        mPlayer?.setDataSource(voice.voicePath)
        mPlayer?.prepare()
        seekBar.max = mPlayer?.duration!!
        mPlayer?.seekTo(progress)
        mPlayer?.setOnPreparedListener {
            mPlayer?.start()
        }
        mPlayer?.setOnCompletionListener {
            stopPlayer()
        }
        updateSeekBar()
    }

    private fun playFile(play: Boolean) {
        if (!play) {
            if (mPlayer == null) {
                startPlayer()
            } else {
                resumePlayer()
            }
        } else {
            pausePlayer()
        }
    }

    private fun startPlayer() {
        filePlay.setImageResource(R.mipmap.ic_media_pause)
        mPlayer = MediaPlayer()
        mPlayer?.setDataSource(voice.voicePath)
        mPlayer?.prepare()
        seekBar.max = mPlayer?.duration!!
        mPlayer?.setOnPreparedListener {
            mPlayer?.start()
        }
        mPlayer?.setOnCompletionListener {
            stopPlayer()
        }
        updateSeekBar()
    }

    private fun updateSeekBar() {
        mHandler.postDelayed(mRunnable, 1000)
    }

    private fun resumePlayer() {
        filePlay.setImageResource(R.mipmap.ic_media_pause)
        mHandler.removeCallbacks(mRunnable)
        mPlayer?.start()
        updateSeekBar()
    }

    private fun pausePlayer() {
        filePlay.setImageResource(R.mipmap.ic_media_play)
        mHandler.removeCallbacks(mRunnable)
        mPlayer?.pause()
    }

    private fun stopPlayer() {
        filePlay.setImageResource(R.mipmap.ic_media_play)
        mHandler.removeCallbacks(mRunnable)
        mPlayer?.stop()
        mPlayer?.reset()
        mPlayer?.release()
        mPlayer = null
        seekBar.progress = seekBar.max
        isPlay = !isPlay
        currentProgressText.text = fileLengthText.text
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.requestFeature(STYLE_NO_TITLE)
        voice = this.arguments!!.getParcelable(TAG)
        return inflater.inflate(R.layout.play_voice_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onPause() {
        super.onPause()
        if (mPlayer != null) {
            stopPlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPlayer != null) {
            stopPlayer()
        }
    }

    private var mRunnable = Runnable {
        if (mPlayer != null) {
            val mCurrentPosition = mPlayer?.currentPosition
            seekBar.progress = mCurrentPosition!!
            currentProgressText.text = TimeUtils.getRecordTimeByMill(mCurrentPosition.toLong())
            updateSeekBar()
        }
    }

}