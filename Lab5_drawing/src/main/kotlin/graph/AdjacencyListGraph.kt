package graph

import draw.DrawingAPI
import java.io.InputStream
import java.util.*

class AdjacencyListGraph : AbstractGraph {
    private val list: Array<List<Int>>

    constructor(list: Array<List<Int>>) {
        this.list = list;
    }

    constructor(inputStream: InputStream) {
        val scanner = Scanner(inputStream)
        val vertexNumber = scanner.nextInt()
        val edgeNumber = scanner.nextInt()
        val edgesTo = Array(vertexNumber) { ArrayList<Int>() }

        for (i in 0 until edgeNumber) {
            val a = scanner.nextInt() - 1
            val b = scanner.nextInt() - 1
            edgesTo[a].add(b)
            edgesTo[b].add(a)
        }

        scanner.close()

        this.list = edgesTo as Array<List<Int>>
    }

    override fun getVertexNumbers(): Int = list.size

    override fun draw(api: DrawingAPI) {
        for (i in list.indices) {
            for (j in list[i]) {
                api.drawLine(getPointByIndex(i), getPointByIndex(j))
            }
            api.drawCircle(getPointByIndex(i), 3)
        }
        api.show()
    }
}
