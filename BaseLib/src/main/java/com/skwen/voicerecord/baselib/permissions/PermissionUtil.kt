package com.skwen.voicerecord.baselib.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.Fragment

import java.util.ArrayList

object PermissionUtil {


    /**
     * 判断是否有某个权限
     * @param context
     * @param permission
     * @return
     */
    fun hasPermission(context: Context, permission: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 弹出对话框请求权限
     * @param activity
     * @param permissions
     * @param requestCode
     */
    fun requestPermissions(activity: Activity, permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(permissions, requestCode)
        }
    }

    fun requestFragmentPermissions(fragment: Fragment, permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fragment.requestPermissions(permissions, requestCode)
        }
    }

    /**
     * 返回缺失的权限
     * @param context
     * @param permissions
     * @return 返回缺少的权限，null 意味着没有缺少权限
     */
    fun getDeniedPermissions(context: Context, permissions: Array<String>): Array<String>? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val deniedPermissionList = ArrayList<String>()
            for (permission in permissions) {
                if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissionList.add(permission)
                }
            }
            val size = deniedPermissionList.size
            if (size > 0) {
                return deniedPermissionList.toTypedArray()
            }
        }
        return null
    }

}
