package com.amier.ruangaji

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import android.view.animation.AnimationUtils
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    var lights = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        animate(lights)
        val image = findViewById<ImageView>(R.id.splash_iv)
        val x = image.width /2
        val y = image.height/2
        Handler().postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            val options = ActivityOptions.makeScaleUpAnimation(image,x,y,image.width,image.height)
            startActivity(intent,options.toBundle())
        }, 1600)
    }

    private fun animate(light:Boolean){
        val anim = AnimationUtils.loadAnimation(this,R.anim.splash_rotate)
        if (light){
            splash_iv.setImageResource(R.mipmap.ic_logo)
        } else if (!light){
            splash_iv.setImageResource(R.mipmap.ic_logo_light)
        }

        splash_iv.startAnimation(anim)
        Handler().postDelayed({
            lights = !lights
            animate(lights)
        },800)

    }
}
