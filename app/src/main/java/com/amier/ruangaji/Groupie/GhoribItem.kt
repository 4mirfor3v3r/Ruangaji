package com.amier.ruangaji.Groupie

import com.amier.ruangaji.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.ghorib_item.view.*

class GhoribItem(val title: String):Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.ghorib_item
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.ghorib_item_pos.text = (position+1).toString()
        viewHolder.itemView.ghorib_item_title.text = title
    }
}