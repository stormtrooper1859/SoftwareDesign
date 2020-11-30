package token

import visitor.TokenVisitor

class NumberToken(val value: Int) : Token {
    override fun accept(tokenVisitor: TokenVisitor) = tokenVisitor.visit(this)
}