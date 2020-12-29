package dk.lrn.amount2text

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal

class Number2StringTest {
    @ParameterizedTest
    @CsvSource(
        "0, ZERO",
        "1, ONE",
        "9, NINE")
    fun testOnes(input: Int, expOutput: String) {
        assertEquals(expOutput, NumberToString.ones(input, true))
    }

    @Test
    fun testOnesZero(){
        assertEquals("", NumberToString.ones(0))
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 10])
    fun testOnesIllegalArgument(input: Int) {
        assertThrows(IllegalArgumentException::class.java) { NumberToString.ones(input) }
    }

    @ParameterizedTest
    @CsvSource(
        "0, ZERO",
        "1, ONE",
        "10, TEN",
        "11, ELEVEN",
        "19, NINETEEN",
        "20, TWENTY",
        "21, TWENTY ONE",
        "30, THIRTY",
        "31, THIRTY ONE",
        "40, FORTY",
        "41, FORTY ONE",
        "50, FIFTY",
        "51, FIFTY ONE",
        "60, SIXTY",
        "61, SIXTY ONE",
        "70, SEVENTY",
        "71, SEVENTY ONE",
        "80, EIGHTY",
        "81, EIGHTY ONE",
        "90, NINETY",
        "91, NINETY ONE")
    fun testTens(input: Int, expOutput: String) {
        assertEquals(expOutput, NumberToString.tens(input, true))
    }

    @Test
    fun testTensWithoutZero() {
        assertEquals("", NumberToString.tens(0, false))
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 100, 777])
    fun testTensIllegalArgument(input: Int) {
        assertThrows(IllegalArgumentException::class.java) { NumberToString.tens(input) }
    }

    @ParameterizedTest
    @CsvSource(
        "1, ONE",
        "10, TEN",
        "19, NINETEEN",
        "20, TWENTY",
        "99, NINETY NINE",
        "100, ONE HUNDRED",
        "101, ONE HUNDRED AND ONE",
        "110, ONE HUNDRED AND TEN",
        "129, ONE HUNDRED AND TWENTY NINE",
        "777, SEVEN HUNDRED AND SEVENTY SEVEN")
    fun testHundreds(input: Int, expOutput: String) {
        assertEquals(expOutput, NumberToString.hundreds(input))
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 1000, 9999])
    fun testHundredsIllegalArgument(input: Int) {
        assertThrows(IllegalArgumentException::class.java) { NumberToString.hundreds(input) }
    }

    @ParameterizedTest
    @CsvSource(
        "47.50, FORTY SEVEN DOLLARS AND FIFTY CENTS",
        "5, FIVE DOLLARS AND ZERO CENTS",
        "205.31, TWO HUNDRED AND FIVE DOLLARS AND THIRTY ONE CENTS",
        "4000.0, FOUR THOUSAND DOLLARS AND ZERO CENTS",
        "1.01, ONE DOLLAR AND ONE CENT")
    fun testCasesFromTheAssignment(input: String, expOutput: String) {
        assertEquals(expOutput, NumberToString.convert(BigDecimal(input)))
    }

    @ParameterizedTest
    @CsvSource(
        "1, ONE DOLLAR AND ZERO CENTS",
        "1.01, ONE DOLLAR AND ONE CENT",
        "2, TWO DOLLARS AND ZERO CENTS",
        "2.01, TWO DOLLARS AND ONE CENT",
        "5, FIVE DOLLARS AND ZERO CENTS",
        "5000, FIVE THOUSAND DOLLARS AND ZERO CENTS")
    fun testSingularPlural(input: String, expOutput: String) {
        assertEquals(expOutput, NumberToString.convert(BigDecimal(input)))
    }

    @ParameterizedTest
    @CsvSource(
        "999, NINE HUNDRED AND NINETY NINE DOLLARS AND ZERO CENTS",
        "1000, ONE THOUSAND DOLLARS AND ZERO CENTS",
        "1001, ONE THOUSAND AND ONE DOLLARS AND ZERO CENTS")
    fun testAround1000(input: String, expOutput: String) {
        assertEquals(expOutput, NumberToString.convert(BigDecimal(input)))
    }

    @ParameterizedTest
    @CsvSource(
        "0.99, NINETY NINE CENTS",
        "0.0, ZERO CENTS",
        "0.01, ONE CENT",
        "0.5, FIFTY CENTS",
        ".1, TEN CENTS")
    fun testCentsOnly(input: String, expOutput: String) {
        assertEquals(expOutput, NumberToString.convert(BigDecimal(input)))
    }

    @ParameterizedTest
    @ValueSource(doubles = [1234567.toDouble(), 1234567.12, 9999000099.99, 1.999, -2.10])
    fun testTensIllegalArgument(input: Double) {
        assertThrows(IllegalArgumentException::class.java) { NumberToString.convert(BigDecimal.valueOf(input)) }
    }


}