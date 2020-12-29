package dk.lrn.amount2text

import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.text.DecimalFormat
import kotlin.text.StringBuilder

/**
 * Strategy: I found out the pattern repeats every time 3 digits are added. So I decided i could manage up to 3 digits (hundreds)
 * I would have so pieces to work with and test. It should also be fairly easy to extend it to handle bigger amounts.
 */
object NumberToString {
    private val regex = """[0-9]{0,6}[.][0-9]{0,2}|[0-9]{0,6}""".toRegex()

    /**
     * Validate that amount in range and the break it up to pieces that can be handled as 3 digits (2 digits for cents) parts.
     * Then it fill in the right "fill" words at the right places
     * */
    fun convert(amount: BigDecimal): String {
        if(!regex.matches(amount.toPlainString())) throw IllegalArgumentException("Number must be a number in range 0..999999 and with maximum of two decimals")
        val sAmount = DecimalFormat("#.00").format(amount).replace(',','.')
        val (dollar, cents) = sAmount.split(".")
        val ones = if (dollar.isNotEmpty()) dollar.takeLast(3).toInt() else 0
        val thousands = if (dollar.length > 3) dollar.substring(0, dollar.length - 3).toInt() else 0

        val sb = StringBuilder()

        if (thousands > 0 && ones > 0) sb.append(hundreds(thousands)).append(" THOUSAND AND ").append(hundreds(ones)).append(" DOLLARS").append(" AND ")
        if (thousands > 0 && ones == 0) sb.append(hundreds(thousands)).append(" THOUSAND DOLLARS").append(" AND ")
        if (thousands == 0 && ones > 1) sb.append(hundreds(ones)).append(" DOLLARS").append(" AND ")
        if (thousands == 0 && ones == 1) sb.append("ONE DOLLAR").append(" AND ")

        if (cents.toInt() != 1) sb.append(tens(cents.toInt(), true)).append(" CENTS")
        if (cents.toInt() == 1) sb.append(tens(cents.toInt(), true)).append(" CENT")
        return sb.toString()
    }

    internal fun hundreds(digits: Int): String {
        if (digits !in 0..999) throw IllegalArgumentException("3 digits must be in range 0..999")

        val sb = StringBuilder()
        if (digits > 99) sb.append(ones(digits.toString().substring(0, 1).toInt()) + " HUNDRED")
        if (digits > 99 && digits.rem(100) != 0) sb.append(" AND ")
        sb.append(tens(digits.toString().takeLast(2).toInt()))
        return sb.toString()
    }

    internal fun tens(digits: Int, showZero: Boolean = false): String {
        val teens = listOf("TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN")
        return when (digits) {
            in 0..9 -> ones(digits, showZero)
            in 10..19 -> teens[digits - 10]
            in 20..29 -> "TWENTY ${ones(digits - 20)}"
            in 30..39 -> "THIRTY ${ones(digits - 30)}"
            in 40..49 -> "FORTY ${ones(digits - 40)}"
            in 50..59 -> "FIFTY ${ones(digits - 50)}"
            in 60..69 -> "SIXTY ${ones(digits - 60)}"
            in 70..79 -> "SEVENTY ${ones(digits - 70)}"
            in 80..89 -> "EIGHTY ${ones(digits - 80)}"
            in 90..99 -> "NINETY ${ones(digits - 90)}"
            else -> throw IllegalArgumentException("Two digits must be in range 0..99")
        }.trim()
    }

    internal fun ones(digit: Int, showZero: Boolean = false): String {
        if (digit !in 0..9) throw IllegalArgumentException("Single digit must be in range 0..9")
        return if (!showZero && digit == 0) "" else listOf("ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE")[digit]
    }
}