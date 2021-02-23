package encapsulation

import encapsulation.EmailAddress.Companion.parse
import encapsulation.EmailSenderInvoke.EmailSender

object FP {
    /// begin: foo
    // Where we know the configuration
    val sender: (Email) -> Unit = createEmailSender(
        inetAddress("smtp.travelator.com"),
        "username",
        "password"
    )

    // Where we send the message
    fun sendThanks() {
        sender( // <1>
            Email(
                to = parse("support@internationalrescue.org"),
                from = parse("support@travelator.com"),
                subject = "Thanks for your help",
                body = "..."
            )
        )
    }
    /// end: foo
}

object FP_invoke {
    // Where we know the configuration
    val sender: (Email) -> Unit = createEmailSender(
        inetAddress("smtp.travelator.com"),
        "username",
        "password"
    )

    // Where we send the message
    /// begin: nonInvoke
    fun sendThanks() {
        sender(
            Email(
                to = parse("support@internationalrescue.org"),
                from = parse("support@travelator.com"),
                subject = "Thanks for your help",
                body = "..."
            )
        )
    }
    /// end: nonInvoke
}

object FP_OO {
    /// begin: bar
    // Where we know the configuration
    val sender: (Email) -> Unit = EmailSender(
        inetAddress("smtp.travelator.com"),
        "username",
        "password"
    )

    // Where we send the message
    fun sendThanks() {
        sender( // <1>
            Email(
                to = parse("support@internationalrescue.org"),
                from = parse("support@travelator.com"),
                subject = "Thanks for your help",
                body = "..."
            )
        )
    }
    /// end: bar
}
