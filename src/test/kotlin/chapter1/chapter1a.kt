package chapter1

import chapter1.java.Presenter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.util.*


/*-
---
title: Data Classes
layout: post
---
-*/

/*-
Kotlin Data classes are probably the quickest win for most Java projects. Let's warm up on a very simple example.

```java
-*/
//#include "java/Presenter.java"
/*-
```
Looking at the code you can see:

1. Presenter is immutable. Because we don't have to worry about changes in state, we can give access directly to fields rather than provide 'getters'.

2. We want to make sure that two presenters with the same fields compare equal, and have the same hashcode. Presenter is a Value Object. There is quite a lot of code to make that happen, but luckily our IDE has generated it for us.

3. We come from the school of Java that assumes that everything we pass, store or return is not null unless explicitly indicated otherwise.

Now we take this code and ask IntelliJ to convert it to Kotlin for us (TODO keystroke)
-*/

object A {
    //`
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
    //`
}
/*-
Comparing this to the Java we see that the Kotlin class definition is quite similar to Java, but our fields have been moved into brackets after the class name. This is a primary constructor (we'll come to secondary constructors later) and any parameters to this constructor may be labelled as `val` and will automatically become available as properties. In other words that first line stands in for all this Java

```java
public class Presenter {

    private final String id;
    private final String name;

    public Presenter(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
```

`val` is short for 'value' and marks a property that you cannot change once set - the equivalent of Java's `final`. We could (but almost always wouldn't) have used `var` for `variable`, in which case the property could be modified.

So far converting our Presenter to Kotlin has saved us 13 lines of code, but we aren't done yet. Value types like this are so useful but so tedious to get right and keep right that Kotlin supports them at a language level. Mark the class with the `data` modifier and the compiler will generate the `equals`, `hashCode` and `toString` methods automatically, leaving us with just
-*/

object B {
    //`
    data class Presenter(val id: String, val name: String)
    //`
}

/*-
Ah that's better, and when we add properties to a Presenter we won't have to remember to update the generated methods or find ourselves with hard-to-diagnose bugs.

You haven't seen them yet, but we had some tests for our Presenter.
```java
-*/
//#include "java/PresenterTests.java"
/*-
```
Due to the seamless interoperation between Kotlin and Java, these continue to pass once with the converted class, so we can conclude that the Kotlin compiler is not only generating the methods we knew about, but also `getId` and `getName`. If we convert the tests themselves to Kotlin
-*/

object C {
    //`
    class PresenterTests {

        private val nat = Presenter("1", "Nat")
        private val duncan = Presenter("2", "Duncan")

        @Test
        fun properties() {
            assertEquals("1", nat.id)
            assertEquals("Duncan", duncan.name)
        }

        @Test
        fun equality() {
            assertEquals(nat, Presenter("1", "Nat"))
            assertNotEquals(nat, duncan)
        }

        @Test
        fun testHashCode() {
            assertEquals(nat.hashCode(), Presenter("1", "Nat").hashCode())
            assertNotEquals(nat.hashCode(), duncan.hashCode())
        }
    }
//`
}
/*-
we can see a few more differences between the languages.

Firstly the `nat` and `duncan` properties are not declared in the primary constructor in this case (because we need a no-argument constructor for JUnit), so they are put in the class body, but marked as `val` because they are immutable. The Java did not mark the fields as `final`, but the conversion is smart enough to use `val` if properties are not changed.

Secondly there is no `new` keyword to construct instances of classes - 'invoking' the class name as a function calls the relevant constructor.

Finally Kotlin accesses properties as eg `name` rather than `getName()`. We'll look at properties in more detail later, but for now it's enough to say that while this looks the same as if we had given public access to the field in Java, it is actually calling a method.
-*/