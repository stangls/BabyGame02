package net.q1cc.stefan.drawEngine

import java.util.*

/**
 * Created by stefan on 17.04.16.
 */

private const val defaultSize = 10

class Pool<T> (factory: Factory<T>?, maxSize:Int) {

    interface Factory<T> {
        fun createObject() : T
    }

    private val freeObjects: ArrayList<T> = ArrayList<T>()
    private val factory = factory
    private val maxSize = maxSize

    constructor(factory: Factory<T>) : this(factory,defaultSize)
    constructor() : this(defaultSize)
    constructor(maxSize:Int) : this(null,maxSize)

    fun newObject() : T =
        getFreeObject()?:factory?.createObject()?:throw UnsupportedOperationException("you can not create a new object without a factory")

    fun free(obj:T) {
        if (freeObjects.size < maxSize)
            freeObjects.add(obj);
    }

    fun getFreeObject() : T? =
        if (freeObjects.size == 0)
            null
        else
            freeObjects.removeAt(freeObjects.size - 1);

}