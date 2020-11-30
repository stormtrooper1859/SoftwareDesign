package token

import visitor.TokenVisitor

class PlusToken : Token {
    override fun accept(tokenVisitor: TokenVisitor) = tokenVisitor.visit(this)
}

class MinusToken : Token {
    override fun accept(tokenVisitor: TokenVisitor) = tokenVisitor.visit(this)
}

class MultiplyToken : Token {
    override fun accept(tokenVisitor: TokenVisitor) = tokenVisitor.visit(this)
}

class DivideToken : Token {
    override fun accept(tokenVisitor: TokenVisitor) = tokenVisitor.visit(this)
}
