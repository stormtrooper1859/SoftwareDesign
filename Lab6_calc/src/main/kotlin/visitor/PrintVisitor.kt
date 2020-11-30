package visitor

import token.*

class PrintVisitor : AbstractTokenVisitor<String>() {
    private var result = StringBuilder()

    private fun append(s: String) {
        if (result.isNotEmpty()) {
            result.append(" ")
        }
        result.append(s)
    }

    override fun visit(token: NumberToken) = append("NUMBER(${token.value})")

    override fun visit(token: PlusToken) = append("PLUS")

    override fun visit(token: MinusToken) = append("MINUS")

    override fun visit(token: MultiplyToken) = append("MUL")

    override fun visit(token: DivideToken) = append("DIV")

    override fun visit(token: OpenBracketToken) = append("LEFT")

    override fun visit(token: CloseBracketToken) = append("RIGHT")

    override fun getResult(): String = result.toString()
}
