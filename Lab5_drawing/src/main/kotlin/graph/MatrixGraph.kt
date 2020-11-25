package graph

import draw.DrawingAPI
import java.io.InputStream
import java.util.*

class MatrixGraph : AbstractGraph {
    private val matrix: Array<BooleanArray>;

    constructor(matrix: Array<BooleanArray>) {
        this.matrix = matrix;
    }

    constructor(inputStream: InputStream) {
        val scanner = Scanner(inputStream)
        val vertexNumber = scanner.nextInt()
        matrix = Array(vertexNumber) { BooleanArray(vertexNumber) }

        for (i in 0 until vertexNumber) {
            for (j in 0 until vertexNumber) {
                val value = scanner.nextInt()
                matrix[i][j] = value != 0
            }
        }

        scanner.close()
    }

    override fun getVertexNumbers(): Int = matrix.size

    override fun draw(api: DrawingAPI) {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j]) {
                    api.drawLine(getPointByIndex(i), getPointByIndex(j))
                }
            }
            api.drawCircle(getPointByIndex(i), 3)
        }
        api.show()
    }
}
