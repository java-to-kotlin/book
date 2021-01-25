package delegation

import java.net.URI
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

operator fun <T : Any> ((String)->T).provideDelegate(thisRef: Any?, property: KProperty<*>) =
    this(property.name).let { ReadOnlyProperty<Any?,T> { _, _ -> it } }


fun main() {
    val alice by ::GitRepository
    println("${alice.name}.uri = ${alice.uri}")
    val bob by ::GitRepository
    println("${bob.name}.uri = ${bob.uri}")
}

data class GitRepository(
    val name: String,
) {
    val uri get() = URI("https://github.com/myteam/$name")
}
