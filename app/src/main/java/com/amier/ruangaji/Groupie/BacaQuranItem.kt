package com.amier.ruangaji.Groupie

import com.amier.ruangaji.Model.Quran
import com.amier.ruangaji.Model.Surah
import com.amier.ruangaji.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.baca_quran_item.view.*

class BacaQuranItem(val quran: Surah): Item<GroupieViewHolder>() {
    var data:Surah? = null
    override fun getLayout(): Int {
        return R.layout.baca_quran_item
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        data = quran
        viewHolder.itemView.baca_quran_item_position.text = "${(position+1)}"
        viewHolder.itemView.baca_quran_item_name.text = quran.title
        viewHolder.itemView.baca_quran_item_description.text = "${quran.arti} | ${quran.type} | ${quran.ayat} Ayat"
    }
}