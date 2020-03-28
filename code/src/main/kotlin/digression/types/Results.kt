@file:Suppress("unused", "UNUSED_PARAMETER")

package digression.types

class Request
class JsonNode
class Order
class Receipt
class SKU


sealed class Result<out T, out E>
data class Success<T>(val value: T) : Result<T, Nothing>()
data class Failure<E>(val reason: E) : Result<Nothing, E>()

inline fun <T, E> Result<T, E>.onFailure(errorHandler: (Failure<E>) -> T) =
    when (this) {
        is Success<T> -> value
        is Failure<E> -> errorHandler(this)
    }

sealed class Error
sealed class TechnicalError : Error()
class IOError: TechnicalError()
class MessageFormatError : TechnicalError()
sealed class OrderError : Error()
class OutOfStock(sku: SKU) : OrderError()
class PaymentDeclined(paymentToken: String) : OrderError()

fun Request.readJson(): Result<JsonNode, TechnicalError> = example()
fun JsonNode.toOrder(): Result<Order, MessageFormatError> = example()
fun processOrder(order: Order): Result<Receipt, OrderError> = example()

fun f(request: Request): Result<Receipt, Error> {
    val orderJson = request.readJson().onFailure { return it }
    val order = orderJson.toOrder().onFailure { return it }
    return processOrder(order)
}

