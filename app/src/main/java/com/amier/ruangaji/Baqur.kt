package com.amier.ruangaji

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.amier.ruangaji.Groupie.AyatItem
import com.amier.ruangaji.Model.Ayat
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_baqur.*
import org.json.JSONArray
import org.json.JSONException
import android.os.*
import com.amier.ruangaji.Model.Surah
import org.json.JSONObject
import java.io.File
import java.io.IOException


class Baqur : AppCompatActivity() {
    lateinit var data: Surah
    lateinit var output: String
    lateinit var name: String
    lateinit var dir: String
    var mediaRecorder: MediaRecorder? = null
    val adapter = GroupAdapter<GroupieViewHolder>()
    var idRecord = 0
    var isRecord = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baqur)

        data = intent.getParcelableExtra("data") as Surah
        supportActionBar?.title = "${data.title} '${data.index.toInt()}"

        activity_baqur_recycler.adapter = adapter
        checkMic()
        setup()
        checkPermissions()

        dir = Environment.getExternalStorageDirectory().absolutePath + "/Ruangngaji/audio"
        val file = File(dir)
        file.mkdirs()

        while (File(dir + "/${data.title}_audio$idRecord.mp3").exists()) {
            idRecord++
        }

        activity_baqur_btn_record.setOnTouchListener { v, event ->
            Log.e("TIME ",event.downTime.toString())
                if (event.action == MotionEvent.ACTION_DOWN) {
                    startRecording()
                    activity_baqur_record_time.visibility = View.VISIBLE
                    activity_baqur_record_time.base = SystemClock.elapsedRealtime()
                    activity_baqur_record_time.start()

                    v.background.colorFilter = PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)

                }
            else if (event.action == MotionEvent.ACTION_UP) {
                    val time = (SystemClock.elapsedRealtime() - activity_baqur_record_time.base)
                    if (time > 1500) {
                        if (event.action == MotionEvent.ACTION_UP) {
                            activity_baqur_record_time.stop()
                            v.background.clearColorFilter()
                            stopRecording()

                            Log.e("Time 1", time.toString())
                        }
                    } else {
                        Handler().postDelayed({
                            activity_baqur_record_time.stop()
                            v.background.clearColorFilter()
                            stopRecording()
                        }, 1500)
                    }
                    Log.e("TIME 2",time.toString())
                }
            true
        }
        getJSONBaqur()
    }

    fun getJSONBaqur(){
        var json:String? = null
        val listAyat = arrayListOf<String>()
        val listArti = arrayListOf<String>()
        try {
            val inputStreamAyat = assets.open("Ayat/surah_${data.index.toInt()}.json")
            json = inputStreamAyat.bufferedReader().use { it.readText() }
            val jsonAyat = JSONObject(json).getJSONObject("verse")

            val inputStreamArti = assets.open("Arti/id_translation_${data.index.toInt()}.json")
            json = inputStreamArti.bufferedReader().use { it.readText() }
            val jsonArti = JSONObject(json).getJSONObject("verse")


            for (i in 1 until jsonAyat.length()){
                val jsonObject = jsonAyat.getString("verse_$i")
                listAyat.add(jsonObject)
            }

            for (i in 1 until jsonArti.length()){
                val jsonObject = jsonArti.getString("verse_$i")
                listArti.add(jsonObject)
            }

            for (i in 0 until listArti.size){
                adapter.add(AyatItem(Ayat(listAyat[i],listArti[i],"")))
            }
        }catch (e:IOException){
            e.printStackTrace()
        }

    }

    private fun checkMic(): Boolean {
        return this.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (applicationContext.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), 0
                )
            }
        }
    }

    private fun setup() {
        if (!checkMic()) {
            baqur_bottom_container.visibility = View.GONE
        }
    }

    private fun startRecording() {
        name = "${data.title}_audio$idRecord.mp3"
        val fileDir = Environment.getExternalStorageDirectory().absolutePath + "/Ruangngaji/audio"

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile("$fileDir/$name")
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            Log.e("LOCATION ", " ${fileDir}/$name")

            try {
                prepare()
            } catch (e: IOException) {
                Log.e("FAILED", e.message)
            }
            start()
        }
    }

    private fun stopRecording() {
        while (File(dir + "/audio${idRecord}.3gp").exists()) {
            idRecord++
        }

        mediaRecorder?.apply {
            reset()
            release()
        }
        mediaRecorder = null
    }

}
