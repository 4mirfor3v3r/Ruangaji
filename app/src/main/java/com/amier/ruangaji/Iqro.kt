package com.amier.ruangaji

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.amier.ruangaji.Groupie.GhoribItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_iqro.*

class Iqro : AppCompatActivity() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    val list = arrayOf("Iqro 1","Iqro 2","Iqro 3","Iqro 4","Iqro 5","Iqro 6")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iqro)
        supportActionBar?.title = "Iqro"

        iqro_recycler.adapter = adapter

        list.forEach {
            adapter.add(GhoribItem(it))
        }
        adapter.setOnItemClickListener { item, view ->

            val pos = adapter.getAdapterPosition(item)
            Toast.makeText(this,"Belom dicari", Toast.LENGTH_SHORT).show()

            true
        }
    }
}
