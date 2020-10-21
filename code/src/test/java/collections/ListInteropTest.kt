package collections

import junit.framework.TestCase

class ListInteropTest : TestCase() {
    fun testJavaCodeCanRemoveElementFromKotlin_Unmodifiable_List() {
        val l = listOf(1,2,3,4).toList()
        FromJava.removeElement(l, 1)
        assertEquals(listOf(1,3,4), l)
    }

    fun testJavaCodeCanRemoveElementFromKotlin_Unmodifiable_Iterator() {
        val l = listOf(1,2,3,4).toList()
        val i = l.iterator()
        i.next()
        FromJava.removeElement(i)
        assertEquals(listOf(2,3,4), l)
    }

    fun testJavaCodeCanSetElementOfListArrayWrapper() {
        val l = listOf(1,2,3,4)
        FromJava.setElement(l, 1, 99)
        assertEquals(listOf(1, 99, 3, 4), l)
    }

    fun testKotlinCanAcceptJavaListAsImmutableList() {
        val l: List<String> = FromJava.listOfString()
        assertEquals(listOf("zero", "one"), l)
    }

    fun testKotlinCanAcceptJavaListAsMutableList() {
        val l: MutableList<String> = FromJava.listOfString()
        l.removeAt(1)
        assertEquals(listOf("zero"), l)
    }
}
