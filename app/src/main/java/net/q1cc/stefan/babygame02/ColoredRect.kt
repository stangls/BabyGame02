package net.q1cc.stefan.babygame02

import android.graphics.Color
import android.graphics.Rect
import android.graphics.RectF
import com.pawegio.kandroid.d
import net.q1cc.stefan.drawEngine.Pool

/**
 * Created by stefan on 19.04.16.
 */
data class ColoredRect(var x1:Float, var y1:Float, var x2:Float, var y2:Float, var color:Int) {

    constructor() : this(0f,0f,0f,0f, Color.WHITE)

    fun checkBounds(){
        // TODO: move this to onchange-event-function
        if (x1>x2){
            val x=x1
            x1=x2
            x2=x
        }
        if (y1>y2){
            val y=y1
            y1=y2
            y2=y
        }
    }

    fun center(x:Float,y:Float,width:Float) = center(x,y,width,width)
    fun center(x:Float,y:Float,width:Float,height:Float) {
        x1=x-width/2
        y1=y-height/2
        x2=x+width/2
        y2=y+height/2
    }

    companion object factory : Pool.Factory<ColoredRect> {
        override fun createObject(): ColoredRect {
            return ColoredRect()
        }
        fun centered(x:Float,y:Float,width:Float,height:Float) { val r= ColoredRect(); r.center(x,y,width,height) }
        fun centered(x:Float,y:Float,width:Float) = centered(x,y,width,width)
    }

    fun expandBy(size: Float) {
        val s2 = size / 2
        x1-= s2
        x2+= s2
        y1-= s2
        y2+= s2
    }

    fun exceeds(rect: Rect): Boolean = rect.left>=x1 && rect.right<=x2 && rect.top>=y1 && rect.bottom<=y2
    fun exceeds(rect: Rect, plus:Float): Boolean = rect.left-plus>=x1 && rect.right+plus<=x2 && rect.top-plus>=y1 && rect.bottom+plus<=y2

    fun asRect(): Rect = Rect(x1.toInt(),y1.toInt(),x2.toInt(),y2.toInt())
    fun asRectF(): RectF = RectF(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat())

    fun getWidth() = (x2-x1)
    fun getHeight() = (y2-y1)

}