package com.skwen.voicerecord.baselib.permissions

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.Fragment

open class PermissionHelper {


    private var mActivity: Activity? = null
    private var mPermissionInterface:PermissionInterface? = null
    private var mFragment: Fragment? = null

    /**
     * activity中请求权限调用
     */
    constructor(activity: Activity, permissionInterface: PermissionInterface) {
        mActivity = activity
        mPermissionInterface = permissionInterface
    }

    /**
     * fragment中请求权限调用
     */
    constructor(fragment: Fragment, permissionInterface: PermissionInterface) {
        mFragment = fragment
        mPermissionInterface = permissionInterface
    }

    /**
     * 开始请求权限。
     * 方法内部已经对Android M 或以上版本进行了判断，外部使用不再需要重复判断。
     * 如果设备还不是M或以上版本，则也会回调到requestPermissionsSuccess方法。
     * activity中请求权限调用
     */
    fun requestPermissions() {
        val deniedPermissions =
            PermissionUtil.getDeniedPermissions(mActivity!!, mPermissionInterface!!.getPermissions())
        if (deniedPermissions != null && deniedPermissions.isNotEmpty()) {
            PermissionUtil.requestPermissions(
                mActivity!!,
                deniedPermissions,
                mPermissionInterface!!.permissionsRequestCode
            )
        } else {
            mPermissionInterface!!.requestPermissionsSuccess()
        }
    }

    /**
     * fragment中请求权限调用
     */
    fun requestFragmentPermissions() {
        val deniedPermissions =
            PermissionUtil.getDeniedPermissions(mFragment!!.context!!, mPermissionInterface!!.getPermissions())
        if (deniedPermissions != null && deniedPermissions.isNotEmpty()) {
            PermissionUtil.requestFragmentPermissions(
                mFragment!!,
                deniedPermissions,
                mPermissionInterface!!.permissionsRequestCode
            )
        } else {
            mPermissionInterface!!.requestPermissionsSuccess()
        }
    }

    /**
     * 在Activity中的onRequestPermissionsResult中调用
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @return true 代表对该requestCode感兴趣，并已经处理掉了。false 对该requestCode不感兴趣，不处理。
     */
    fun requestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray): Boolean {
        if (requestCode == mPermissionInterface!!.permissionsRequestCode) {
            var isAllGranted = true//是否全部权限已授权
            for (result in grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    isAllGranted = false
                    break
                }
            }
            if (isAllGranted) {
                //已全部授权
                mPermissionInterface!!.requestPermissionsSuccess()
            } else {
                //权限有缺失
                mPermissionInterface!!.requestPermissionsFail()
            }
            return true
        }
        return false
    }

}
