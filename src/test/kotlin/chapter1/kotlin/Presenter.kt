package chapter1.kotlin

import java.util.*

class Presenter(val id: String, val name: String) {

    override fun toString(): String {
        return "Presenter{" +
            "id='" + id + '\''.toString() +
            ", name='" + name + '\''.toString() +
            '}'.toString()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val presenter = o as Presenter?
        return id == presenter!!.id && name == presenter.name
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name)
    }
}