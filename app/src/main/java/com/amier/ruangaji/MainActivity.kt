package com.amier.ruangaji

import android.Manifest
import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.util.Pair
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()
        home_baca_quran.setOnClickListener {
            intentTransition(it,BacaQuran::class.java)
        }
        home_tahfidz_quran.setOnClickListener {
            intentTransition(it,Tahfidz::class.java)
        }
        home_ghorib_quran.setOnClickListener {
            intentTransition(it,Ghorib::class.java)
        }
        home_iqro_quran.setOnClickListener { intentTransition(it,Iqro::class.java) }
        home_tajwid_quran.setOnClickListener { intentTransition(it,Tajwid::class.java) }
    }
    private fun checkPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (applicationContext.checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET),0)
            }
        }
    }

    private fun intentTransition(button:View, targetClass:Class<*>){
        val intent = Intent(this,targetClass)
        val options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(button,"btn_zoom"))
        startActivity(intent,options.toBundle())
    }
}
