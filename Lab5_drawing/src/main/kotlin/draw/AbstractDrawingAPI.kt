package draw

abstract class AbstractDrawingAPI : DrawingAPI {
    protected val width: Int
    protected val height: Int

    constructor() {
        width = DEFAULT_WIDTH
        height = DEFAULT_HEIGHT
    }

    constructor(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    companion object {
        protected const val DEFAULT_WIDTH = 500
        protected const val DEFAULT_HEIGHT = 500
    }
}
