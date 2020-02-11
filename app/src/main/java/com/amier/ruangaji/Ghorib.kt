package com.amier.ruangaji

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.amier.ruangaji.Groupie.GhoribItem
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_ghorib.*

class Ghorib : AppCompatActivity() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    val list = arrayOf<String>("Imalah", "Isymam", "Saktah", "Tashil","Naql","Badal","Shilah")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ghorib)
        ghorib_recycler.adapter = adapter
        supportActionBar?.title = "Ghorib"

        list.forEach {
            adapter.add(GhoribItem(it))
        }

        adapter.setOnItemClickListener { item, view ->

            val pos = adapter.getAdapterPosition(item)
            Toast.makeText(this,"Materinya Mana lurr saya bukan pak nas :v",Toast.LENGTH_SHORT).show()
        }
    }
}
