import draw.JFrameDrawingAPI
import draw.JavaFXDrawingAPI
import graph.AdjacencyListGraph
import graph.MatrixGraph

fun main() {
//    testGraph()
//    MatrixGraph(System.`in`).draw(JavaFXDrawingAPI())
    MatrixGraph(System.`in`).draw(JFrameDrawingAPI())
//    AdjacencyListGraph(System.`in`).draw(JavaFXDrawingAPI())
//    AdjacencyListGraph(System.`in`).draw(JFrameDrawingAPI())
}

fun testGraph() {
    val a: Array<ArrayList<Int>> = Array(5){_ -> ArrayList()}
    a[0].add(1)
    a[0].add(3)
    a[1].add(0)
    a[1].add(2)
    a[2].add(1)
    a[2].add(3)
    a[3].add(2)
    a[3].add(0)
    a[3].add(4)
    a[4].add(3)
    val graph = AdjacencyListGraph(a as Array<List<Int>>)
    graph.draw(JFrameDrawingAPI())
}
