package draw

import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Shape
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import javax.swing.JFrame

class JFrameDrawingAPI : AbstractDrawingAPI {
    constructor() : super()
    constructor(width: Int, height: Int) : super(width, height)

    private val elementsList: ArrayList<Shape> = ArrayList()

    private fun convertToNativePoint(point: Point) = Point(
            width / 2.0 + width / 2.0 * point.x,
            height / 2.0 - height / 2.0 * point.y
    )

    override fun drawCircle(center: Point, radius: Int) {
        val (x, y) = convertToNativePoint(center)
        val circle = Ellipse2D.Double(
                x - radius * CIRCLE_RADIUS_MULTIPLY,
                y - radius * CIRCLE_RADIUS_MULTIPLY,
                radius * CIRCLE_RADIUS_MULTIPLY * 2,
                radius * CIRCLE_RADIUS_MULTIPLY * 2
        )
        elementsList.add(circle)
    }

    override fun drawLine(begin: Point, end: Point) {
        val (x1, y1) = convertToNativePoint(begin)
        val (x2, y2) = convertToNativePoint(end)
        val line = Line2D.Double(x1, y1, x2, y2)
        elementsList.add(line)
    }

    override fun show() {
        val frame = DrawingJFrame(elementsList)

        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.size = Dimension(width, height)
        frame.layout = null
        frame.isVisible = true
    }

    class DrawingJFrame(private val drawElements: List<Shape>) : JFrame() {
        override fun paint(g: Graphics?) {
            super.paint(g)
            g.let {
                val g2d: Graphics2D = g as Graphics2D
                drawElements.forEach {
                    g2d.fill(it)
                    g2d.draw(it)
                }
            }
        }
    }

    companion object {
        private const val CIRCLE_RADIUS_MULTIPLY = 5.0
    }
}
