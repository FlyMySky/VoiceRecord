package com.skwen.voicerecord.voicelib.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skwen.voicerecord.voicelib.R
import com.skwen.voicerecord.voicelib.db.VoiceHelper
import com.skwen.voicerecord.voicelib.entity.Voice
import com.skwen.voicerecord.voicelib.holder.VoiceDataCallBack
import com.skwen.voicerecord.voicelib.holder.VoiceHolder

class VoiceAdapter(private var layoutManager: LinearLayoutManager) : RecyclerView.Adapter<VoiceHolder>(),
    VoiceDataCallBack {

    private lateinit var onItemClickListener: VoiceItemClickListener

    private lateinit var onItemLongClickListener: VoiceItemLongClickListener

    private var sparseArray: SparseArray<Voice> = SparseArray()

    private var isSelected = false

    init {
        VoiceHelper.instance.registerCallBack(this)
    }

    override fun onInsertData() {
        notifyDataSetChanged()
//        notifyItemInserted(itemCount - 1)
//        layoutManager.scrollToPosition(itemCount - 1)
    }

    override fun onEditData() {
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): VoiceHolder {
        return VoiceHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.voice_list_item, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return VoiceHelper.instance.dataCount().toInt()
    }

    fun getItem(position: Int): Voice {
        return VoiceHelper.instance.queryAll()!![position]
    }

    override fun onBindViewHolder(holder: VoiceHolder, position: Int) {
        val voice = VoiceHelper.instance.queryAll()!![position]
        holder.bindData(voice)
        if (isSelected) {
            holder.itemChecked.visibility = View.VISIBLE
        } else {
            holder.itemChecked.visibility = View.GONE
        }
        holder.itemChecked.isChecked = false
        holder.itemChecked.setOnClickListener {
            if (holder.itemChecked.isChecked) {
                sparseArray.put(position, voice)
            } else {
                sparseArray.remove(position)
            }
        }
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(voice)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener.onItemLongClick(voice)
            true
        }
    }

    fun addOnItemClickListenter(onItemClickListener: VoiceItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun addOnItemLongClickListenter(onItemLongClickListener: VoiceItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener
    }

    fun setChecked(isSelected: Boolean) {
        this.isSelected = isSelected
        notifyDataSetChanged()
    }

    fun getSelectList(): MutableList<Voice> {
        val mutableList = mutableListOf<Voice>()
        if (sparseArray.size() > 0) {
            for (index in 0..(sparseArray.size() - 1)) {
                mutableList.add(sparseArray.valueAt(index))
            }
        }
        return mutableList
    }
}