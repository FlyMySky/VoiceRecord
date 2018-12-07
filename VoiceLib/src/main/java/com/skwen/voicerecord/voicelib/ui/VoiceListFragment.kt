package com.skwen.voicerecord.voicelib.ui

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.skwen.voicerecord.baselib.tools.FileUtils
import com.skwen.voicerecord.baselib.tools.ToastUtils
import com.skwen.voicerecord.baselib.ui.LazyFragment
import com.skwen.voicerecord.voicelib.R
import com.skwen.voicerecord.voicelib.adapter.VoiceAdapter
import com.skwen.voicerecord.voicelib.adapter.VoiceItemClickListener
import com.skwen.voicerecord.voicelib.adapter.VoiceItemLongClickListener
import com.skwen.voicerecord.voicelib.db.VoiceHelper
import com.skwen.voicerecord.voicelib.entity.Voice
import com.skwen.voicerecord.voicelib.service.RecordService
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem
import kotlinx.android.synthetic.main.voice_list_fragment.*

class VoiceListFragment : LazyFragment() {


    private var isStartRecord = true

    private var mRecordPromptCount = 0

    private lateinit var mAdapter: VoiceAdapter

    private var isSelected = false

    override fun getLayoutRes(): Int {
        return R.layout.voice_list_fragment
    }

    override fun initViews() {


        val layoutManager = LinearLayoutManager(context)
        voiceListView.layoutManager = layoutManager
        voiceListView.setSwipeMenuCreator(swipeMenuCreator)
        voiceListView.setSwipeMenuItemClickListener { menuBridge, position ->
            run {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu()
                val item = mAdapter.getItem(position)
                VoiceHelper.instance.deleteData(item)
                FileUtils.delete(item.voicePath)
                mAdapter.notifyDataSetChanged()
            }
        }
        mAdapter = VoiceAdapter(layoutManager)
        voiceListView.adapter = mAdapter
        mAdapter.addOnItemClickListenter(object : VoiceItemClickListener {
            override fun onItemClick(voice: Voice) {
                if (isStartRecord) {
                    val fragment = PlayVoiceFragment.instance(voice)
                    fragment.show(childFragmentManager, PlayVoiceFragment.TAG)
                } else {
                    ToastUtils.showToast(context!!, "录音中不能播放...")
                }
            }
        })
        mAdapter.addOnItemLongClickListenter(object : VoiceItemLongClickListener {
            override fun onItemLongClick(voice: Voice) {
                if (isStartRecord) {
                    val fragment = EditVoiceFragment.instance(voice)
                    fragment.show(childFragmentManager, EditVoiceFragment.TAG)
                }
            }
        })
        voiceTime?.base = SystemClock.elapsedRealtime()
        startRecord.setOnClickListener {
            run {
                if (isHasPermission) {
                    startRecordVoice(isStartRecord)
                    isStartRecord = !isStartRecord
                } else {
                    ToastUtils.showToast(context!!, "请授予录音权限")
                }
            }
        }
        toolBarBack.setOnClickListener {
            run {
                activity!!.finish()
            }
        }

        toolBarMenu.setOnClickListener {
            setActionLayout()
        }

        cancelAction.setOnClickListener {
            setActionLayout()
        }

        conformAction.setOnClickListener {
            val selectList = mAdapter.getSelectList()
            if (selectList.size == 0) {
                ToastUtils.showToast(context!!, "请选择")
                return@setOnClickListener
            } else {
                for (item in selectList) {
                    VoiceHelper.instance.deleteData(item)
                    FileUtils.delete(item.voicePath)
                }
            }
            setActionLayout()
        }
    }

    private fun setActionLayout() {
        isSelected = !isSelected
        mAdapter.setChecked(isSelected)
        if (isSelected) {
            actionLayout.visibility = View.VISIBLE
        } else {
            actionLayout.visibility = View.GONE
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

    private val swipeMenuCreator = SwipeMenuCreator { swipeLeftMenu, swipeRightMenu, position ->
        val deleteItem = SwipeMenuItem(context)
            .setImage(R.mipmap.voice_list_item_delete_icon) // 图标。
            .setText("删除") // 文字。
            .setTextSize(10) // 文字大小。
            .setWidth(160)
            .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
        swipeRightMenu.addMenuItem(deleteItem)// 添加一个按钮到右侧侧菜单。.

        // 上面的菜单哪边不要菜单就不要添加。
    }


}