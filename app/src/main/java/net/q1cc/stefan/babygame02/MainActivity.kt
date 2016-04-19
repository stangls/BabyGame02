package net.q1cc.stefan.babygame02

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var drawEngine: RectangleZoomer? = null

    // activity lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main)
        surf.setOnTouchListener(drawEngine)
        surf.fullScreen()
        drawEngine = RectangleZoomer(surf)
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.hide()
        actionBar?.hide()
    }

    override fun onStart() {
        super.onStart()
        surf.fullScreen()
        drawEngine?.start()
    }

    override fun onStop() {
        super.onStop()
        drawEngine?.stop()
    }

}

