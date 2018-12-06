package com.skwen.voicerecord.voicelib.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.skwen.voicerecord.voicelib.entity.Voice
import kotlinx.android.synthetic.main.voice_list_item.view.*

class VoiceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var itemName = itemView.itemName
    var itemTime = itemView.itemTime
    var itemRecordTime = itemView.itemRecordTime

    fun bindData(item: Voice) {
        itemName.text = item.voiceName
        itemTime.text = item.voiceTime
        itemRecordTime.text = item.recordTime
    }
}