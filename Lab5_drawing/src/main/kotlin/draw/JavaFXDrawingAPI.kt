package draw

import javafx.application.Application
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.stage.Stage

class JavaFXDrawingAPI : AbstractDrawingAPI {
    constructor() : super()
    constructor(width: Int, height: Int) : super(width, height)

    private val elementsList: ArrayList<Node> = ArrayList()

    private fun convertToNativePoint(point: Point) = Point(
            width / 2.0 + width / 2.0 * point.x,
            height / 2.0 - height / 2.0 * point.y
    )

    override fun drawCircle(center: Point, radius: Int) {
        val circle = Circle()
        val (x, y) = convertToNativePoint(center);
        circle.centerX = x
        circle.centerY = y
        circle.radius = radius * CIRCLE_RADIUS_MULTIPLY
        circle.stroke = Color.BLACK
        circle.strokeWidth = 1.0
        elementsList.add(circle)
    }

    override fun drawLine(begin: Point, end: Point) {
        val (x1, y1) = convertToNativePoint(begin)
        val (x2, y2) = convertToNativePoint(end)
        val line = Line(x1, y1, x2, y2);
        elementsList.add(line)
    }

    override fun show() {
        DrawingJavaFX.drawingList = elementsList
        DrawingJavaFX.width = width
        DrawingJavaFX.height = height
        Application.launch(DrawingJavaFX::class.java)
    }

    class DrawingJavaFX : Application() {
        override fun start(primaryStage: Stage) {
            val pane = Pane()

            drawingList?.forEach {
                pane.children.add(it)
            }

            val scene = Scene(pane, width.toDouble(), height.toDouble(), true)
            primaryStage.scene = scene
            primaryStage.title = "DrawingJavaFX"
            primaryStage.show()
        }

        companion object {
            var drawingList: List<Node>? = null
            var width: Int = 100
            var height: Int = 100
        }
    }

    companion object {
        private const val CIRCLE_RADIUS_MULTIPLY = 5.0
    }
}