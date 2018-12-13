package com.skwen.voicerecord.videolib

import com.skwen.voicerecord.baselib.ui.LazyFragment

class VideoFragment : LazyFragment() {


    override fun getLayoutRes(): Int {
        return R.layout.fragment_video
    }

    override fun initViews() {
    }

    override fun onLazyLoad() {
    }

    override fun onNoPermission() {
    }

    override val permissionsRequestCode: Int
        get() = 1002

    override fun getPermissions(): Array<String> {
        return emptyArray()
    }


}
