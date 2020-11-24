package encapsulation

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

    fun sendDistress(sender: (Email) -> Unit) {
        sender.invoke( // <1>
            Email("support@internationalrescue.org", "Travelator Customer Incident", "...")
        )
    }
    /// end: foo
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

    fun sendDistress(sender: (Email) -> Unit) {
        sender.invoke( // <1>
            Email("support@internationalrescue.org", "Travelator Customer Incident", "...")
        )
    }
    /// end: bar
}
