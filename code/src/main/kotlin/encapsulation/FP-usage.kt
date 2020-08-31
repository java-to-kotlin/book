package encapsulation

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