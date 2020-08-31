package encapsulation

object OO {
    /// begin: foo
    // Where we know the configuration

    val sender: EmailSender = EmailSender(
        inetAddress("smtp.travelator.com"),
        "username",
        "password"
    )

    // Where we send the message

    fun sendDistress(sender: EmailSender) {
        sender.send(
            Email("support@internationalrescue.org", "Travelator Customer Incident", "...")
        )
    }
    /// end: foo
}

