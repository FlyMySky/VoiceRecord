package com.skwen.voicerecord.voicelib.ui

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.support.v7.widget.LinearLayoutManager
import android.view.WindowManager
import com.skwen.voicerecord.baselib.tools.ToastUtils
import com.skwen.voicerecord.baselib.ui.LazyFragment
import com.skwen.voicerecord.voicelib.R
import com.skwen.voicerecord.voicelib.adapter.VoiceAdapter
import com.skwen.voicerecord.voicelib.adapter.VoiceItemClickListener
import com.skwen.voicerecord.voicelib.adapter.VoiceItemLongClickListener
import com.skwen.voicerecord.voicelib.entity.Voice
import com.skwen.voicerecord.voicelib.service.RecordService
import kotlinx.android.synthetic.main.voice_list_fragment.*

class VoiceListFragment : LazyFragment() {


    private var isStartRecord = true

    private var mRecordPromptCount = 0

//    private var mList: MutableList<Voice>? = null

    private lateinit var mAdapter: VoiceAdapter

    override fun getLayoutRes(): Int {
        return R.layout.voice_list_fragment
    }

    override fun initViews() {
//        mList = mutableListOf()
        val layoutManager = LinearLayoutManager(context)
        voiceListView.layoutManager = layoutManager
        mAdapter = VoiceAdapter(layoutManager)
        voiceListView.adapter = mAdapter
        mAdapter.addOnItemClickListenter(object : VoiceItemClickListener {
            override fun onItemClick(voice: Voice) {
                val fragment = PlayVoiceFragment.instance(voice)
                fragment.show(childFragmentManager, PlayVoiceFragment.TAG)
            }
        })
        mAdapter.addOnItemLongClickListenter(object : VoiceItemLongClickListener {
            override fun onItemLongClick(voice: Voice) {
                val fragment = EditVoiceFragment.instance(voice)
                fragment.show(childFragmentManager, EditVoiceFragment.TAG)
            }
        })
        startRecord.setOnClickListener {
            run {
                startRecordVoice(isStartRecord)
                isStartRecord = !isStartRecord
            }
        }
    }

    private fun startRecordVoice(isStartRecord: Boolean) {
        val intent = Intent(activity, RecordService::class.java)
        if (isStartRecord) {
            startRecord?.setImageResource(R.mipmap.ic_media_stop)
            voiceTime?.base = SystemClock.elapsedRealtime()
            voiceTime?.start()
            voiceTime?.setOnChronometerTickListener {
                run {
                    when (mRecordPromptCount) {
                        0 -> voiceState?.text = "录音中."
                        1 -> voiceState?.text = "录音中.."
                        2 -> {
                            voiceState?.text = "录音中.."
                            mRecordPromptCount = -1
                        }
                    }
                    mRecordPromptCount++
                }
            }
            activity?.startService(intent)
            activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            voiceState?.text = "录音中."
            mRecordPromptCount++
        } else {
            voiceTime?.stop()
            voiceTime?.base = SystemClock.elapsedRealtime()
            activity?.stopService(intent)
            voiceState?.text = "点击录音"
            startRecord?.setImageResource(R.mipmap.ic_mic_white_36dp)
            mRecordPromptCount = 0
            onLazyLoad()
        }

    }

    override fun onLazyLoad() {
        if (isHasPermission) {
            ToastUtils.showToast(context!!, "获取权限成功")
        } else {
            requestUsePermissions()
        }
    }

    override fun onNoPermission() {
        ToastUtils.showToast(context!!, "获取权限失败")

    }

    override val permissionsRequestCode: Int
        get() = 1001

    override fun getPermissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        } else {
            arrayOf()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (permissionHelper?.requestPermissionsResult(requestCode, permissions as Array<String>, grantResults)!!) {
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}