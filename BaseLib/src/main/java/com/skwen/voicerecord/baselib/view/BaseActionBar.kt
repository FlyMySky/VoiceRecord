package com.skwen.voicerecord.baselib.view

import android.content.Context
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.skwen.voicerecord.baselib.view.vu.IBaseActionBar

/**
 * 作者：vicent
 * 时间：2019/1/2
 */
open class BaseActionBar : RelativeLayout {

    private var leftLayout: LinearLayout = LinearLayout(context)

    private var leftView: TextView = TextView(context)

    private var titleView: TextView = TextView(context)

    private var rightLayout: LinearLayout = LinearLayout(context)

    private var rightView: TextView = TextView(context)

    private lateinit var iBaseActionBar: IBaseActionBar

    init {

        //添加左边view
        val leftParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        this.leftLayout.orientation = LinearLayout.HORIZONTAL
        this.leftLayout.gravity = Gravity.CENTER
        this.leftLayout.addView(leftView, leftParams)
//        this.leftView.textSize = DensityUtil.dp2px(14f).toFloat()

        val leftParentParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        leftParentParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        leftParentParams.addRule(RelativeLayout.CENTER_VERTICAL)
        leftLayout.setOnClickListener {
            iBaseActionBar.onLeftClick()
        }
        this.addView(leftLayout, leftParentParams)

        //添加中间view
        val titleParentParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        titleParentParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        this.addView(titleView, titleParentParams)
//        this.titleView.textSize = DensityUtil.dp2px(14f).toFloat()


        //添加右侧view
        val rightParentParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        rightParentParams.addRule(RelativeLayout.CENTER_VERTICAL)
        rightParentParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        rightLayout.setOnClickListener {
            iBaseActionBar.onRightClick()
        }
        this.rightLayout.orientation = LinearLayout.HORIZONTAL
        this.rightLayout.gravity = Gravity.CENTER
        this.rightLayout.addView(rightView, leftParams)
//        this.rightView.textSize = DensityUtil.dp2px(14f).toFloat()

        this.addView(rightLayout, rightParentParams)

    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    fun addClickListener(iBaseActionBar: IBaseActionBar) {
        this.iBaseActionBar = iBaseActionBar
    }

    fun setLeftViewText(leftText: String) {
        leftView.text = leftText
    }

    fun setLeftViewImg(@DrawableRes id: Int) {
        leftView.setBackgroundResource(id)
    }

    fun setRightViewText(leftText: String) {
        rightView.text = leftText
    }

    fun setRightViewImg(@DrawableRes id: Int) {
        rightView.setBackgroundResource(id)
    }

    fun setTitleView(title: String) {
        titleView.text = title
    }

}