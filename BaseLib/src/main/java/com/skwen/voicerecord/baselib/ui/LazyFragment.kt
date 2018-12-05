package com.skwen.voicerecord.baselib.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skwen.voicerecord.baselib.permissions.PermissionHelper
import com.skwen.voicerecord.baselib.permissions.PermissionInterface

/**
 * 判断用户是否可见
 * 判断view是否创建完
 * 如果可见并创建完成则请求所需权限
 * 获取权限后加载数据
 * 如果获取权限失败 返回并提示
 */
abstract class LazyFragment : Fragment(), PermissionInterface {

    private var isViewCreated = false
    private var isVisibleToUser = false
    var permissionHelper: PermissionHelper? = null
    var isHasPermission = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        if (isViewCreated) {
            lazyLoad()
        }
    }

    private fun lazyLoad() {
        if (isViewCreated && isVisibleToUser) {
            onLazyLoad()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        if (isVisibleToUser) {
            lazyLoad()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onResume() {
        super.onResume()
        requestUsePermissions()
    }

    fun requestUsePermissions() {
        permissionHelper = PermissionHelper(this, this)
        permissionHelper?.requestFragmentPermissions()
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int


    abstract fun initViews()


    abstract fun onLazyLoad()

    override fun requestPermissionsSuccess() {
        isHasPermission = true
        initViews()
        onLazyLoad()
    }

    override fun requestPermissionsFail() {
        isHasPermission = false
        onNoPermission()
    }

    abstract fun onNoPermission()

}