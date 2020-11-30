package visitor

import TokensList
import org.junit.Assert.*
import org.junit.Test
import token.*
import java.lang.RuntimeException

class CalcVisitorTest {

    @Test
    fun testFunctionality() {
        val tokensList = TokensList(listOf(
                NumberToken(123),
                NumberToken(122),
                MinusToken(),
                NumberToken(4),
                PlusToken(),
                NumberToken(42),
                NumberToken(7),
                DivideToken(),
                MultiplyToken(),
        ))

        val calcVisitor = CalcVisitor()
        tokensList.iterate(calcVisitor)
        assertEquals(30, calcVisitor.getResult())
    }

    @Test(expected = RuntimeException::class)
    fun testExceedTokens() {
        val tokensList = TokensList(listOf(
                NumberToken(123),
                NumberToken(122),
                MinusToken(),
                NumberToken(4),
        ))

        val calcVisitor = CalcVisitor()
        tokensList.iterate(calcVisitor)
        calcVisitor.getResult()
    }

    @Test(expected = RuntimeException::class)
    fun testUnexpectedToken() {
        val tokensList = TokensList(listOf(
                NumberToken(123),
                OpenBracketToken(),
                NumberToken(4),
                MinusToken(),
        ))

        val calcVisitor = CalcVisitor()
        tokensList.iterate(calcVisitor)
        calcVisitor.getResult()
    }

    @Test(expected = RuntimeException::class)
    fun testTooMuchOperations() {
        val tokensList = TokensList(listOf(
                NumberToken(123),
                NumberToken(4),
                MultiplyToken(),
                MinusToken(),
        ))

        val calcVisitor = CalcVisitor()
        tokensList.iterate(calcVisitor)
        calcVisitor.getResult()
    }

}