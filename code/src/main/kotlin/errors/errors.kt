package errors

import com.natpryce.Failure
import com.natpryce.Result
import com.natpryce.Success
import java.lang.NumberFormatException


/// begin: parseIntResult
fun parseInt(s: String): Result<Int, String> =
    try {
        Success(Integer.parseInt(s))
    } catch (x: NumberFormatException) {
        Failure("Could not parse %s as int")
    }
/// end: parseIntResult

/// begin: ShortDigitString
data class ShortDigitString(
    val value: String
) : CharSequence by value {
    init {
        check(
            length in 1..9 &&
            all { it.isDigit() }
        )
    }
}
/// end: ShortDigitString


/// begin: parseInt
fun parseInt(s: ShortDigitString) =
    Integer.parseInt(s.value)
/// end: parseInt

/// begin: numberOfMonths
fun numberOfMonths(years: String) =
    12 * parseInt(ShortDigitString(years))
/// end: numberOfMonths

/// begin: numberOfMonthsShort
fun numberOfMonths(years: ShortDigitString) =
    12 * parseInt(years)
/// end: numberOfMonthsShort

/// begin: errorMessage
val Result<*, Exception>.errorMessage : String? get() =
    when (this) {
        is Success -> null
        is Failure -> this.reason.message ?: "no message"
    }
/// end: errorMessage


