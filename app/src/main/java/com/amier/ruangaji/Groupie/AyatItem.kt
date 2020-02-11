package com.amier.ruangaji.Groupie

import android.graphics.Color
import android.graphics.PorterDuff
import com.amier.ruangaji.Model.Ayat
import com.amier.ruangaji.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.ayat_item.view.*

class AyatItem(val ayat:Ayat): Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.ayat_item
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.ayat_item_ar.text = ayat.ar
        viewHolder.itemView.ayat_item_arti.text = ayat.id
        viewHolder.itemView.ayat_item_position.text = "${(position+1)}"

        if (position%2==0){
            viewHolder.itemView.ayat_item_container.setBackgroundResource(R.drawable.ayat_item_container_one)
        }else if (position%2==1){
            viewHolder.itemView.ayat_item_container.setBackgroundResource(R.drawable.ayat_item_container_two)
        }
    }
}