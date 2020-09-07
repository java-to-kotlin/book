package encapsulation

import java.net.InetAddress

object Anon {
    /// begin: anon
    fun createEmailSender(
        serverAddress: InetAddress,
        username: String,
        password: String
    ): ISendEmail =
        object : ISendEmail {
            override fun send(email: Email) = sendEmail(
                email,
                serverAddress,
                username,
                password
            )
        }
    /// end: anon
}

object Anon_2 {
    /// begin: anon2
    fun interface ISendEmail {
        fun send(email: Email)
    }

    fun createEmailSender(
        serverAddress: InetAddress,
        username: String,
        password: String
    ) = ISendEmail { email ->
        sendEmail(
            email,
            serverAddress,
            username,
            password
        )
    }
    /// end: anon2
}