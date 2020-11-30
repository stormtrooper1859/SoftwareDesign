import tokenizer.Tokenizer
import visitor.CalcVisitor
import visitor.ParseVisitor
import visitor.PrintVisitor

fun main() {
//    val expression = "1+(3+2)"
    val expression = "4 +3 * 5+1"
//    val expression = "(4 +3) * (5+1)"
    println(expression)

    val tokenizer = Tokenizer(expression)
    tokenizer.processInput()
    val normalParsedExpression = tokenizer.getTokensList()

    val normalPrintVisitor = PrintVisitor()
    normalParsedExpression.iterate(normalPrintVisitor)
    println(normalPrintVisitor.getResult())

    val parseVisitor = ParseVisitor()
    normalParsedExpression.iterate(parseVisitor)
    val RPNExpression = TokensList(parseVisitor.getResult())

    val RPNPrintVisitor = PrintVisitor()
    RPNExpression.iterate(RPNPrintVisitor)
    println(RPNPrintVisitor.getResult())

    val calcVisitor = CalcVisitor()
    RPNExpression.iterate(calcVisitor)
    println(calcVisitor.getResult())
}
