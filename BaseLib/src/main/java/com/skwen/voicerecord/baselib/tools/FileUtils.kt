package com.skwen.voicerecord.baselib.tools

import java.io.File

object FileUtils {


    /** 删除文件，可以是文件或文件夹
     * @param delFile 要删除的文件夹或文件名
     * @return 删除成功返回true，否则返回false
     */
    fun delete(filePath: String): Boolean {
        val file = File(filePath)
        return if (!file.exists()) {
            true
        } else {
            if (file.isFile) {
                deleteSingleFile(filePath)
            } else {
                deleteDirectory(filePath)
            }
        }
    }

    /** 删除单个文件
     * @param filePath 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private fun deleteSingleFile(filePath: String): Boolean {
        val file = File(filePath)
        if (file.exists() && file.isFile) {
            return file.delete()
        }
        return false
    }

    /** 删除目录及目录下的文件
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    private fun deleteDirectory(filePath: String): Boolean {
        var path = filePath
        if (!path.endsWith(File.separator)) {
            path += File.separator
        }
        val dirFile = File(path)
        var flag = true
        // 删除文件夹中的所有文件包括子目录
        val files = dirFile.listFiles()
        for (file in files) {
            // 删除子文件
            if (file.isFile) {
                flag = deleteSingleFile(file.absolutePath)
                if (!flag)
                    break
            } else if (file.isDirectory) {// 删除子目录
                flag = deleteDirectory(file.absolutePath)
                if (!flag)
                    break
            }
        }
        if (dirFile.delete()) {
            return true
        }
        return flag
    }
}