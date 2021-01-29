package encapsulation

import encapsulation.FPUsage.sendThanks
import java.util.function.Consumer

object Thunking {
    /// begin: foo
    // Kotlin function type
    val sender: (Email) -> Unit = createEmailSender(
        inetAddress("smtp.travelator.com"),
        "username",
        "password"
    )
    /// end: foo

    /*
    /// begin: foo
    val consumer: Consumer<Email> = sender // Doesn't compile <1>

    /// end: foo
     */
    /// begin: foo
    val consumer: Consumer<Email> = Consumer<Email> { email -> // <2>
        sender(email)
    }

    /// end: foo
    init {
        /// begin: foo
        // Java method taking Consumer
        sendThanks(sender) // <3>
        /// end: foo
    }
}