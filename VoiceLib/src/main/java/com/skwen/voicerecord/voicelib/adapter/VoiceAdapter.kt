package com.skwen.voicerecord.voicelib.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.skwen.voicerecord.voicelib.R
import com.skwen.voicerecord.voicelib.db.VoiceHelper
import com.skwen.voicerecord.voicelib.holder.VoiceDataCallBack
import com.skwen.voicerecord.voicelib.holder.VoiceHolder

class VoiceAdapter(private var layoutManager: LinearLayoutManager) : RecyclerView.Adapter<VoiceHolder>(),
    VoiceDataCallBack {

    init {
        VoiceHelper.instance.registerCallBack(this)
    }

    override fun onInsertData() {
        notifyItemInserted(itemCount - 1)
        layoutManager.scrollToPosition(itemCount - 1)
    }

    override fun onEditData() {

    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): VoiceHolder {
        return VoiceHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.voice_list_item, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return VoiceHelper.instance.dataCount().toInt()
    }

    override fun onBindViewHolder(holder: VoiceHolder, position: Int) {
        holder.bindData(VoiceHelper.instance.queryAll()!![position])
    }

}