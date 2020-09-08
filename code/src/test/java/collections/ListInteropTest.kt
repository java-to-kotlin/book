package collections

import junit.framework.TestCase

class ListInteropTest : TestCase() {
    fun testJavaCodeCanRemoveElementFromKotlin_Unmodifiable_List() {
        val l = listOf(1,2,3,4).toList()
        UseList.removeElement(l, 1)
        assertEquals(listOf(1,3,4), l)
    }

    fun testJavaCodeCanRemoveElementFromKotlin_Unmodifiable_Iterator() {
        val l = listOf(1,2,3,4).toList()
        val i = l.iterator()
        i.next()
        UseList.removeElement(i)
        assertEquals(listOf(2,3,4), l)
    }
}
