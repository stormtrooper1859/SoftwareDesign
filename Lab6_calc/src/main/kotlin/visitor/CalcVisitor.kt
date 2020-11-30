package visitor

import token.*
import java.util.*

class CalcVisitor : AbstractTokenVisitor<Int>() {
    private var stack = Stack<Int>()

    override fun visit(token: NumberToken) {
        stack.push(token.value)
    }

    override fun visit(token: PlusToken) = visitBinaryOperation { a, b -> a + b }

    override fun visit(token: MinusToken) = visitBinaryOperation { a, b -> a - b }

    override fun visit(token: MultiplyToken) = visitBinaryOperation { a, b -> a * b }

    override fun visit(token: DivideToken) = visitBinaryOperation { a, b -> a / b }

    private fun visitBinaryOperation(function: (Int, Int) -> Int) {
        val b = stack.pop()
        val a = stack.pop()
        stack.push(function.invoke(a, b))
    }

    override fun visit(token: OpenBracketToken) {
        throw RuntimeException("unsupported token")
    }

    override fun visit(token: CloseBracketToken) {
        throw RuntimeException("unsupported token")
    }

    override fun getResult(): Int {
        if (stack.size != 1) {
            throw RuntimeException("error during calculation")
        }

        return stack.peek()
    }
}
