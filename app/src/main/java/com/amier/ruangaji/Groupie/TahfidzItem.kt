package com.amier.ruangaji.Groupie

import android.widget.SeekBar
import com.amier.ruangaji.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.tahfidz_item.view.*


class TahfidzItem(private val title:String):Item<GroupieViewHolder>() {
    lateinit var progress:SeekBar
    override fun getLayout(): Int {
        return R.layout.tahfidz_item
    }


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tahfidz_item_title.text = title
    }
}