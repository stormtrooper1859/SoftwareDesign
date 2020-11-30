package token

import visitor.TokenVisitor

class OpenBracketToken : Token {
    override fun accept(tokenVisitor: TokenVisitor) = tokenVisitor.visit(this)
}

class CloseBracketToken : Token {
    override fun accept(tokenVisitor: TokenVisitor) = tokenVisitor.visit(this)
}
