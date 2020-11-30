package visitor

import TokensList
import org.junit.Assert.*
import org.junit.Test
import token.*

class PrintVisitorTest {

    @Test
    fun testFunctionality() {
        val tokensList = TokensList(listOf(
                NumberToken(123),
                PlusToken(),
                OpenBracketToken(),
                MinusToken(),
                MultiplyToken(),
                PlusToken(),
                CloseBracketToken(),
                DivideToken(),
        ))

        val printVisitor = PrintVisitor()
        tokensList.iterate(printVisitor)
        assertEquals("NUMBER(123) PLUS LEFT MINUS MUL PLUS RIGHT DIV", printVisitor.getResult())
    }

}