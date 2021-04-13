package collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.ClassCastException

class ListInteropTest {
    @Test
    fun testJavaCodeCanRemoveElementFromKotlin_Unmodifiable_List() {
        val l = listOf(1,2,3,4).toList()
        SomeJavaCode.removeElement(l, 1)
        assertEquals(listOf(1,3,4), l)
    }

    @Test
    fun testJavaCodeCanRemoveElementFromKotlin_Unmodifiable_Iterator() {
        val l = listOf(1,2,3,4).toList()
        val i = l.iterator()
        i.next()
        SomeJavaCode.removeElement(i)
        assertEquals(listOf(2,3,4), l)
    }

    @Test
    fun testJavaCodeCanSetElementOfListArrayWrapper() {
        val l = listOf(1,2,3,4)
        SomeJavaCode.setElement(l, 1, 99)
        assertEquals(listOf(1, 99, 3, 4), l)
    }

    @Test
    fun testKotlinCanAcceptJavaListAsImmutableList() {
        /// begin: immutable
        val aList: List<String> = SomeJavaCode.mutableListOfStrings("0", "1")
        /// end: immutable
        /*
        /// begin: immutable
        aList.removeAt(1) // doesn't compile
        /// end: immutable
        */

        assertEquals(listOf("0", "1"), aList)
    }

    @Test
    fun testKotlinCanAcceptJavaListAsMutableList() {
        /// begin: mutable
        val aMutableList: MutableList<String> = SomeJavaCode.mutableListOfStrings("0", "1")
        aMutableList.removeAt(1)
        assertEquals(listOf("0"), aMutableList)
        /// end: mutable
    }

    @Test
    fun testKotlinCanCastAwayJavaImmutabilty() {
        /// begin: cast
        val aList: List<String> = SomeJavaCode.mutableListOfStrings("0", "1")
        val aMutableList: MutableList<String> = aList as MutableList<String>
        aMutableList.removeAt(1)
        assertEquals(listOf("0"), aMutableList)
        /// end: cast
    }

    @Test
    fun testKotlinLiteralsCanBeDowncastButCannotBeResizedAtRuntime() {
        assertThrows<UnsupportedOperationException> {
            /// begin: kotlinCastFail
            val aList: List<String> = listOf("0", "1")
            val aMutableList: MutableList<String> = aList as MutableList<String>
            aMutableList.removeAt(1) // throws UnsupportedOperationException
            /// end: kotlinCastFail
        }
    }

    @Test
    fun testKotlinLiteralsCanBeEditedInPlaceAtRuntime() {
        val aList: List<String> = listOf("0", "1")
        val aMutableList: MutableList<String> = aList as MutableList<String>
        aMutableList[1] = "banana"
        assertEquals(listOf("0", "banana"), aMutableList)
    }

    @Test
    fun testKotlinHOFResultsCanBeModified() {
        /// begin: kotlinHOF
        val aList: List<String> = listOf("0", "1").myMap { it}
        val aMutableList: MutableList<String> = aList as MutableList<String>
        aMutableList.removeAt(1)
        assertEquals(listOf("0"), aMutableList)
        /// end: kotlinHOF
    }

    @Test
    fun testStandardKotlinHOFResultsCanBeModified() {
        val aList: List<String> = listOf("0", "1").map { it}
        val aMutableList: MutableList<String> = aList as MutableList<String>
        aMutableList.removeAt(1)
        assertEquals(listOf("0"), aMutableList)
    }

    @Test
    fun testCannotDowncastWillyNilly() {
        assertThrows<ClassCastException> {
            /// begin: downCastSupported
            val aList: List<String> = MyList("0", "1")
            val aMutableList: MutableList<String> = aList as MutableList<String> // throws ClassCastException
            /// end: downCastSupported
        }
    }

    @Test
    fun testJavaCanAcceptMutableList() {
        /// begin: javaAcceptMutableList
        val aMutableList: MutableList<String> = mutableListOf("0", "1")
        SomeJavaCode.needsAList(aMutableList)
        /// end: javaAcceptMutableList
    }

    @Test
    fun testJavaCanAcceptImmutableList() {
        /// begin: javaAcceptList
        val aList: List<String> = listOf("0", "1")
        SomeJavaCode.needsAList(aList)
        /// end: javaAcceptList
    }

    @Test
    fun testJustAReadOnlyView() {
        /// begin: upCast
        val aMutableList = mutableListOf("0", "1")
        val aList: List<String> = aMutableList
        /// end: upCast

        /// begin: passAsList
        val holdsState = AValueType(aList)
        assertEquals(holdsState.first, holdsState.strings.first())
        /// end: passAsList

        assertThrows<AssertionError> {
            /// begin: mutate
            aMutableList[0] = "banana"
            assertEquals(holdsState.first, holdsState.strings.first()) // Expected "0", actual "banana"
            /// end: mutate
        }
    }
}

class MyList<T>(vararg items: T): List<T> by items.toList()

/// begin: aClass
class AValueType(
    val strings: List<String>
) {
    val first: String? = strings.firstOrNull()
}
/// end: aClass