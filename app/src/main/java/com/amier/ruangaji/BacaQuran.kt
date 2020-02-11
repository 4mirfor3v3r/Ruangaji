package com.amier.ruangaji

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import com.amier.ruangaji.Groupie.BacaQuranItem
import com.amier.ruangaji.Model.Surah
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_baca_quran.*
import org.json.JSONArray
import java.io.IOException

@Suppress("SpellCheckingInspection")
class BacaQuran : AppCompatActivity() {
    private val adapter = GroupAdapter<GroupieViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baca_quran)

        baca_quran_recycler.adapter = adapter
        adapter.setOnItemClickListener { item, view ->
            val extra = item as BacaQuranItem
            val intent = Intent(this,Baqur::class.java)
            intent.putExtra("data", extra.data)

            val options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(view,"btn_zooms"))
            startActivity(intent,options.toBundle())

        }
        getJSON()
    }

    private fun getJSON(){
        val json: String?

        try {
            val inputStream = assets.open("Surah.json")
            json = inputStream.bufferedReader().use { it.readText() }

            val jsonArray = JSONArray(json)

            for (i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)
                val surah = Surah(
                    jsonObject.getString("index"),
                    jsonObject.getString("title"),
                    jsonObject.getString("pages"),
                    jsonObject.getString("type"),
                    jsonObject.getString("count").toString()
                )
                adapter.add(BacaQuranItem(surah))
            }
        }catch (e:IOException){
            e.printStackTrace()
        }
    }
}
