package net.q1cc.stefan.drawEngine;

import android.graphics.Canvas
import android.view.SurfaceHolder
import com.pawegio.kandroid.runAsync

/**
 * Created by stefan on 19.04.16.
 */
abstract class DrawEngine(surface : Surface) {

    protected val surface = surface
    protected val frameTime = 1000/30 // 30FPS


    private var running = false

    abstract fun initState()
    abstract fun computeState()
    abstract fun drawState(canvas: Canvas)

    fun start() {
        // main rendering loop
        running=true
        runAsync {
            var waitTime = 500L
            initState()
            while (running) {
                Thread.sleep(waitTime)
                val startTime = System.currentTimeMillis();
                computeState();
                val holder: SurfaceHolder = surface?.holder ?: continue;
                val canvas: Canvas? = holder.lockCanvas()
                if (canvas != null) {
                    drawState(canvas)
                    holder.unlockCanvasAndPost(canvas)
                }
                waitTime = Math.max(frameTime - System.currentTimeMillis() + startTime, 5)
            }
        }
    }

    fun stop() {
        running = false
    }

    fun isRunning() = running

}