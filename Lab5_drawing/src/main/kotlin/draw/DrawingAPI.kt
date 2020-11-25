package draw

interface DrawingAPI {
    fun drawCircle(center: Point, radius: Int)
    fun drawLine(begin: Point, end: Point)
    fun show()
}
