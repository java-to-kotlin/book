package encapsulation

import java.net.InetAddress

class EmailSystem(
    private val serverAddress: InetAddress,
    private val username: String,
    private val password: String
) {
    fun send(email: Email): Unit = TODO()

    fun delete(email: Email): Unit = TODO()

    fun list(): List<Email> = TODO()
}
