package net.q1cc.stefan.babygame02

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import net.q1cc.stefan.drawEngine.DrawEngine
import net.q1cc.stefan.drawEngine.Pool
import net.q1cc.stefan.drawEngine.Surface
import java.util.*

val expansionStep = 10

class RectangleZoomer(surface: Surface) : DrawEngine(surface), View.OnTouchListener {

    val pool: Pool<Rectangle> = Pool(Rectangle.factory)
    val rectangles = HashSet<Rectangle>()
    val backgroundPaint = Paint()
    private val colors = intArrayOf(
        Color.BLUE, Color.rgb(255,0,255),
        Color.RED, Color.rgb(255,255,0),
        Color.GREEN, Color.rgb(0,255,255)
    )
    val rand = Random()

    override fun initState() {

    }

    override fun computeState() {
        val rect = surface.clipBounds ?: return
        var toRemove:Rectangle?=null
        synchronized(rectangles){
            for(r in rectangles){
                r.expandBy(expansionStep)
                if (toRemove==null && r.exceeds(rect)){
                    toRemove=r
                }
            }
        }
        val tR = toRemove
        if (tR!=null){
            rectangles.remove(tR)
            pool.free(tR)
        }
    }

    override fun drawState(canvas: Canvas) {
        canvas.drawRect(canvas.clipBounds,backgroundPaint)
        synchronized(rectangles){
            for(rect in rectangles){
                val paint = Paint()
                paint.color=rect.color
                canvas.drawRect(rect.asRect(),paint)
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
            rect.center(event.x.toInt(),event.y.toInt(),10)
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