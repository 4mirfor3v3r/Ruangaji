package com.amier.ruangaji

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.amier.ruangaji.Groupie.TahfidzItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_tahfidz.*
import java.io.File
import java.io.FileInputStream


@Suppress("DEPRECATION", "SpellCheckingInspection")
class Tahfidz : AppCompatActivity() {
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private var adapterPosition = 0
    var mediaPlayer:MediaPlayer? =null
    private var listPath:ArrayList<String> = arrayListOf()
    private var isPlayed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tahfidz)
        tahfidz_recycler.adapter = adapter
        supportActionBar?.title = "Tahfidz"
        val path = Environment.getExternalStorageDirectory().absolutePath + "/Ruangngaji/audio/"
        File(path).walkTopDown().forEach {
            if (it.isFile){
            adapter.add(TahfidzItem(it.name))
                listPath.add(it.path)
            }
        }

        adapter.setOnItemClickListener { item, _ ->
            tahfidz_bottom_container.visibility = View.VISIBLE
            adapterPosition = adapter.getAdapterPosition(item)

            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
            isPlayed = false
            tahfidz_seekbar.progress = 0

            tahfidz_btn_play_pause.setImageResource(R.drawable.ic_play_big)
            prepareMusic(adapterPosition)

            mediaPlayer?.setOnPreparedListener {
                tahfidz_btn_play_pause.setOnClickListener { toggleMusic() }
                tahfidz_btn_stop.setOnClickListener { stopMusic() }
                Log.e("PREPARED","TRUE")
            }
            tahfidz_seekbar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) mediaPlayer?.seekTo(progress)
                Log.e("PROGRESS "," $progress")
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })
            Thread(Runnable {
                while (mediaPlayer != null){
                try {
                    val msg = Message()
                    msg.what = mediaPlayer!!.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(100)
                }catch (e:InterruptedException){
                    Log.e("EXCEPTION ", " ${e.message}")
                }
            }
        }).start()
        }
        adapter.setOnItemLongClickListener { item, view ->
            val pos = adapter.getAdapterPosition(item)

            val popupMenu = PopupMenu(view.context, view)
            MenuInflater(this).inflate(R.menu.menu_delete, popupMenu.menu)
            popupMenu.show()

            popupMenu.setOnMenuItemClickListener {
                if (it.itemId == R.id.delete){
                    val file = File(listPath[pos])
                    file.delete()
                    listPath.removeAt(pos)
                    adapter.remove(item)
                    stopMusic()
                }
                true
            }
            true
        }
    }

    private fun stopMusic(){
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
        isPlayed = false
        tahfidz_seekbar.progress = 0
        tahfidz_btn_play_pause.setImageResource(R.drawable.ic_play_big)
        tahfidz_bottom_container.visibility = View.INVISIBLE
    }

    private var handler = @SuppressLint("HandlerLeak")
    object : Handler(){
        override fun handleMessage(msg: Message) {
            val currentPosition = msg.what
            tahfidz_seekbar.progress = currentPosition

        }
    }

    private fun prepareMusic(pos:Int){
        mediaPlayer = MediaPlayer()
        val fis = FileInputStream(listPath[pos])
        mediaPlayer?.isLooping = true
        mediaPlayer?.setDataSource(fis.fd)
        mediaPlayer?.prepare()
        tahfidz_seekbar.max = mediaPlayer!!.duration

    }

    private fun toggleMusic(){
        if (mediaPlayer != null) {
            if (!isPlayed) {
                mediaPlayer?.start()
                tahfidz_btn_play_pause.setImageResource(R.drawable.ic_pause_big)
            } else {
                mediaPlayer?.pause()
                tahfidz_btn_play_pause.setImageResource(R.drawable.ic_play_big)
            }
            isPlayed = !isPlayed
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMusic()
    }


}


