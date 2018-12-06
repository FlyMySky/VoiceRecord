package com.skwen.voicerecord.voicelib.ui

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skwen.voicerecord.baselib.tools.ToastUtils
import com.skwen.voicerecord.voicelib.R
import com.skwen.voicerecord.voicelib.db.Const
import com.skwen.voicerecord.voicelib.db.VoiceHelper
import com.skwen.voicerecord.voicelib.entity.Voice
import kotlinx.android.synthetic.main.edit_voice_fragment.*
import java.io.File

class EditVoiceFragment : DialogFragment() {

    private lateinit var voice: Voice

    companion object {
        const val TAG = "editVoice"
        fun instance(voice: Voice): EditVoiceFragment {
            val fragment = EditVoiceFragment()
            val bundle = Bundle()
            bundle.putParcelable(TAG, voice)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.requestFeature(STYLE_NO_TITLE)
        voice = this.arguments!!.getParcelable(EditVoiceFragment.TAG)
        return inflater.inflate(R.layout.edit_voice_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        cancelBtn.setOnClickListener {
            dismiss()
        }
        conformBtn.setOnClickListener {
            if (TextUtils.isEmpty(inputName.text)) {
                ToastUtils.showSnackbar(it, "请输入需要修改的名称")
            } else {
                updateVoice()
            }
        }
    }

    private fun updateVoice() {

        val name = inputName.text.toString().trim()
        val path = Const.voiceDictionaryPath + name + ".mp4"
        val newFile = File(path)
        if (newFile.exists() && !newFile.isDirectory) {
            ToastUtils.showToast(context!!, "文件名重复")
            return
        }
        val oldFile = File(voice.voicePath)
        oldFile.renameTo(newFile)
        voice.voiceName = "$name.mp4"
        voice.voicePath = path
        VoiceHelper.instance.updateData(voice)
        dismiss()
    }

}