package encapsulation

import java.util.function.Consumer

object Thunking {
    /// begin: foo1
    // Kotlin function type
    val sender: (Email) -> Unit = createEmailSender(
        inetAddress("smtp.travelator.com"),
        "username",
        "password"
    )
    /// end: foo1

    /*
    /// begin: foo2
    val consumer: Consumer<Email> = sender // Doesn't compile <1>
    /// end: foo2
     */

    /// begin: foo3
    val consumer: Consumer<Email> = Consumer<Email> { email ->
        sender(email)
    }
    /// end: foo3

    init {
        /// begin: foo4
        Rescuing(sender)
        /// end: foo4
    }
}

