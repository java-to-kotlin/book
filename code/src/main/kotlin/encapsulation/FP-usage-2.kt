package encapsulation

object FP_OO_2 {
    /// begin: foo
    val instance = EmailSender(
        inetAddress("smtp.travelator.com"),
        "username",
        "password"
    )
    val sender: (Email) -> Unit = { instance.send(it) }
    /// end: foo
}

object FP_OO_3 {
    val instance = EmailSender(
        inetAddress("smtp.travelator.com"),
        "username",
        "password"
    )
    /// begin: bar
    val sender: (Email) -> Unit = instance::send
    /// end: bar
}