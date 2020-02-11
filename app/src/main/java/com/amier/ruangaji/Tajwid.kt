package com.amier.ruangaji

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.amier.ruangaji.Groupie.GhoribItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_tajwid.*

class Tajwid : AppCompatActivity() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    val list = arrayOf("Izhar","Idgham Bi Ghunnah","Idgham BiLa Ghunnah","Iqlab","Ikhfa'","Ra' Tafkhim","Ra' Tarqiq","Mad Ashli/Thobi'i", "Mad Far'i","Ikhfa' Syafawi","Ikhfa' Mimi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tajwid)
        supportActionBar?.title = "Tajwid"

        tajwid_recycler.adapter = adapter

        list.forEach {
            adapter.add(GhoribItem(it))
        }
        adapter.setOnItemClickListener { item, view ->

            val pos = adapter.getAdapterPosition(item)
            Toast.makeText(this,"Materinya Mana lurr saya bukan pak nas :v", Toast.LENGTH_SHORT).show()

            true
        }
    }
}
