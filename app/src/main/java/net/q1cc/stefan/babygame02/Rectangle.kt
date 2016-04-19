package net.q1cc.stefan.babygame02

import android.graphics.Color
import android.graphics.Rect
import com.pawegio.kandroid.d
import net.q1cc.stefan.drawEngine.Pool

/**
 * Created by stefan on 19.04.16.
 */
data class Rectangle(var x1:Int,var y1:Int,var x2:Int,var y2:Int,var color:Int) {

    constructor() : this(0,0,0,0, Color.WHITE)

    init{
        d(this.toString())
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

    fun center(x:Int,y:Int,width:Int) = center(x,y,width,width)
    fun center(x:Int,y:Int,width:Int,height:Int) {
        x1=x-width/2
        y1=y-height/2
        x2=x+width/2
        y2=y+height/2
    }

    companion object factory : Pool.Factory<Rectangle> {
        override fun createObject(): Rectangle {
            return Rectangle()
        }
        fun centered(x:Int,y:Int,width:Int,height:Int) { val r=Rectangle(); r.center(x,y,width,height) }
        fun centered(x:Int,y:Int,width:Int) = centered(x,y,width,width)
    }

    fun expandBy(size: Int) {
        val s2 = size / 2
        x1-= s2
        x2+= s2
        y1-= s2
        y2+= s2
    }

    fun exceeds(rect: Rect): Boolean = rect.left<=x1 && rect.right>=x2 && rect.top<=y1 && rect.bottom>=y2

    fun asRect(): Rect = Rect(x1,y1,x2,y2)

}