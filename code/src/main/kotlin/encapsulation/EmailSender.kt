package encapsulation

import java.net.InetAddress

class EmailSender(
    private val serverAddress: InetAddress,
    private val username: String,
    private val password: String
) {
    fun send(email: Email) {
        sendEmail(email, serverAddress, username, password)
    }
}