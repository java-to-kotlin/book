package encapsulation

import java.net.InetAddress

fun SOME_CODE(): Nothing = TODO()

/// begin: send
fun send(email: Email) {
    SOME_CODE()
}
/// end: send

/// begin: sendEmail
fun sendEmail(
    email: Email,
    serverAddress: InetAddress,
    username: String,
    password: String
) {
    SOME_CODE()
}
/// end: sendEmail


/// begin: createEmailSender0
fun createEmailSender(
    serverAddress: InetAddress,
    username: String,
    password: String
): (Email) -> Unit { // <1>
    SOME_CODE()
}
/// end: createEmailSender0

object A {
    /// begin: createEmailSender1
    fun createEmailSender(
        serverAddress: InetAddress,
        username: String,
        password: String
    ): (Email) -> Unit {

        fun result(email: Email) {
            sendEmail(
                email,
                serverAddress,
                username,
                password
            )
        }
        return ::result
    }
    /// end: createEmailSender1
}

object B {
    /// begin: createEmailSender2
    fun createEmailSender(
        serverAddress: InetAddress,
        username: String,
        password: String
    ): (Email) -> Unit {

        val result: (Email) -> Unit =
            { email ->
                sendEmail(
                    email,
                    serverAddress,
                    username,
                    password
                )
            }
        return result
    }
    /// end: createEmailSender2
}

object C {
    /// begin: createEmailSender3
    fun createEmailSender(
        serverAddress: InetAddress,
        username: String,
        password: String
    ): (Email) -> Unit =
        { email ->
            sendEmail(
                email,
                serverAddress,
                username,
                password
            )
        }
    /// end: createEmailSender3
}


fun inetAddress(s: String): InetAddress =
    InetAddress.getByName(s)

