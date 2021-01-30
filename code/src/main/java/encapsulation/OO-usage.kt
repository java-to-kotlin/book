package encapsulation

import java.net.InetAddress

object OO {
    /// begin: foo
    // Where we know the configuration

    val sender: EmailSender = EmailSender(
        inetAddress("smtp.travelator.com"),
        "username",
        "password"
    )

    // Where we send the message

    fun sendThanks(sender: EmailSender) {
        sender.send(
            Email(
                to = "support@internationalrescue.org",
                subject = "Thanks for your help",
                body = "..."
            )
        )
    }
    /// end: foo
}

object OO_Class {

    /// begin: oo_class
    // Where we know the configuration

    val subsystem = SubSystem(
        EmailSender(
            inetAddress("smtp.travelator.com"),
            "username",
            "password"
        )
    )

    // Where we send the message

    class SubSystem(
        private val emailSender: EmailSender
    ) {
        fun sendThanks() {
            emailSender.send(
                Email(
                    to = "support@internationalrescue.org",
                    subject = "Thanks for your help",
                    body = "..."
                )
            )
        }
    }
    /// end: oo_class
}

object OO_2 {
    /// begin: bar
    val function: (Email) -> Unit = createEmailSender(
        inetAddress("smtp.travelator.com"),
        "username",
        "password"
    )

    val sender: ISendEmail = object : ISendEmail {
        override fun send(email: Email) {
            function(email)
        }
    }
    /// end: bar
}

object OO_3 {
    /// begin: baz
    fun interface ISendEmail {
        fun send(email: Email)
    }

    val sender = ISendEmail { function(it) }
    /// end: baz

    val function: (Email) -> Unit = createEmailSender(
        inetAddress("smtp.travelator.com"),
        "username",
        "password"
    )
}
