package visitor

import token.*

abstract class AbstractTokenVisitor<T> : TokenVisitor {
    override fun visit(token: Token) {
        when (token) {
            is NumberToken -> visit(token)
            is OpenBracketToken -> visit(token)
            is CloseBracketToken -> visit(token)
            is PlusToken -> visit(token)
            is MinusToken -> visit(token)
            is MultiplyToken -> visit(token)
            is DivideToken -> visit(token)
            else -> {
                throw RuntimeException("Unexpected token")
            }
        }
    }

    abstract fun visit(token: NumberToken)
    abstract fun visit(token: PlusToken)
    abstract fun visit(token: MinusToken)
    abstract fun visit(token: MultiplyToken)
    abstract fun visit(token: DivideToken)
    abstract fun visit(token: OpenBracketToken)
    abstract fun visit(token: CloseBracketToken)
    abstract fun getResult(): T
}
