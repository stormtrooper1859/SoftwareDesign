package visitor

import token.*
import java.util.*
import kotlin.collections.ArrayList

class ParseVisitor : AbstractTokenVisitor<List<Token>>() {
    private val result = ArrayList<Token>()
    private val stack = Stack<Token>()

    override fun visit(token: NumberToken) {
        result.add(token)
    }

    private fun plusMinusToken(token: Token) {
        while (!stack.empty() && stack.peek() !is OpenBracketToken) {
            result.add(stack.pop())
        }

        stack.push(token)
    }

    private fun mulDivToken(token: Token) {
        while (!stack.empty() && stack.peek() is MultiplyToken && stack.peek() is DivideToken) {
            result.add(stack.peek())
        }

        stack.push(token)
    }

    override fun visit(token: PlusToken) = plusMinusToken(token)

    override fun visit(token: MinusToken) = plusMinusToken(token)

    override fun visit(token: MultiplyToken) = mulDivToken(token)

    override fun visit(token: DivideToken) = mulDivToken(token)

    override fun visit(token: OpenBracketToken) {
        stack.push(token)
    }

    override fun visit(token: CloseBracketToken) {
        while (stack.peek() !is OpenBracketToken) {
            result.add(stack.pop())
        }
        stack.pop()
    }

    override fun getResult(): List<Token> {
        while (!stack.empty() && stack.peek() !is OpenBracketToken) {
            result.add(stack.pop())
        }

        if (!stack.empty()) {
            throw RuntimeException("error during parsing")
        }

        return result
    }
}
