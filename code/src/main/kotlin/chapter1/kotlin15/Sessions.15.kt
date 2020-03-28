package chapter1.kotlin15

import chapter1.kotlin8.Session

object Sessions {
    fun List<Session>.firstWithTitle(title: String) =
        firstOrNull { it.title == title }
}