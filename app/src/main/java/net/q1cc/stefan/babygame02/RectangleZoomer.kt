package net.q1cc.stefan.babygame02

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import com.pawegio.kandroid.d
import net.q1cc.stefan.drawEngine.DrawEngine
import net.q1cc.stefan.drawEngine.Pool
import net.q1cc.stefan.drawEngine.Surface
import java.util.*

val expansionFactorMS = 100f/100/1000 // 100% per second

class RectangleZoomer(surface: Surface) : DrawEngine(surface), View.OnTouchListener {

    val pool: Pool<ColoredRect> = Pool(ColoredRect.factory)
    val rectangles = LinkedList<ColoredRect>()
    val backgroundPaint = Paint()
    private val colors = intArrayOf(
        Color.BLUE, Color.rgb(255,0,255),
        Color.RED, Color.rgb(255,255,0),
        Color.GREEN, Color.rgb(0,255,255),
        Color.BLACK
    )
    val rand = Random()
    val sin_45 = 0.5f*Math.sqrt(2.0).toFloat()
    val plusFactor = 0.5f-0.5f*sin_45

    override fun initState() {
        backgroundPaint.color=Color.BLACK
        backgroundPaint.colorFilter=null
        backgroundPaint.strokeWidth=0f
    }

    override fun computeState(rect: Rect) {
        synchronized(rectangles){
            if (!rectangles.isEmpty()){
                for(r in rectangles){
                    val expandSize = Math.max(1f,expansionFactorMS*Math.max(r.getWidth(),r.getHeight())*timeDelta())
                    r.expandBy(expandSize)
                }
                val first = rectangles.first
                if (first.exceeds(rect,plusFactor*first.getWidth())){
                    rectangles.remove(first)
                    pool.free(first)
                    backgroundPaint.color=first.color
                }
            }
        }
    }

    override fun drawState(canvas: Canvas) {
        canvas.drawRect(canvas.clipBounds,backgroundPaint)
        synchronized(rectangles){
            for(rect in rectangles){
                val paint = Paint()
                paint.color=rect.color
                val rad=Math.min(rect.getWidth(),rect.getHeight()).toFloat()/4
                canvas.drawRoundRect(rect.asRectF(),rad,rad,paint)
            }
        }
    }

    // callback methods

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        if (event==null) return false
        if (event.action== MotionEvent.ACTION_DOWN){
            // TODO: foreach pointer-index in motion-event
            // obtain fresh rectangle
            val rect = pool.newObject()
            rect.center(event.x,event.y,50f)
            do{
                val color=colors[rand.nextInt(colors.size)]
                if (color!=backgroundPaint.color){
                    rect.color=color
                    break
                }
            }while(true)
            synchronized(rectangles){
                rectangles.add(rect)
            }

        }
        return true
    }
}