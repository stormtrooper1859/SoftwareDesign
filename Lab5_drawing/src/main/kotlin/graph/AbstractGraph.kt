package graph

import draw.Drawable
import draw.Point
import kotlin.math.cos
import kotlin.math.sin

abstract class AbstractGraph : Drawable {
    abstract fun getVertexNumbers(): Int

    protected fun getPointByIndex(index: Int): Point {
        val polarAngleBetweenTwoVertex = 2 * Math.PI / getVertexNumbers()
        return getPointByAngle(polarAngleBetweenTwoVertex * index)
    }

    companion object {
        protected const val RADIUS_SCALE = 0.8
        protected fun getPointByAngle(deg: Double): Point = Point(cos(deg) * RADIUS_SCALE, sin(deg) * RADIUS_SCALE)
    }
}