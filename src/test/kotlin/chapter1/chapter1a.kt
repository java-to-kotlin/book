package chapter1

import chapter1.java.Presenter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.util.*


/*-
Kotlin Data classes are probably the quickest win for most Java projects. Let's warm up on a very simple example.

A> We'll skip over configuring your build system to handle Kotlin for now.

```java
-*/
//#include "java/Presenter.java"
/*-
```
Looking at the code you can see:

1. Presenter is immutable.

2. We want to make sure that two presenters with the same fields compare equal, and have the same hashcode - Presenter is a Value Object. There is quite a lot of code to make that happen, but luckily our IDE has generated it for us.

3. We come from the school of Java that assumes that everything we pass, store or return is not null unless explicitly indicated otherwise.

IntelliJ has a command to convert a Java source file to Kotlin. It is called (at the time of writing) 'Convert Java File to Kotlin File', and is bound to Ctrl+Shift+Alt+K on Windows and Linux, Cmd-Shift-Option-K on Mac. If we run that command on Presenter.java we get
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
Take some time to compare the two files.

To our eyes the most noticeable difference is that the in the Kotlin file fields have been moved into brackets after the class name. This is a primary constructor (we'll come to secondary constructors later). Any parameters to the primary constructor may be labelled as `val` and will automatically become available as properties. In other words that first line stands in for all this Java

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

`val` is short for 'value' and marks a property that you cannot change once set - the equivalent of Java's `final`. We could (but almost always wouldn't) have used `var` for `variable`, in which case the property could be modified and a Java setter would be generated.

So far converting our Presenter to Kotlin has saved us 13 lines of code, but we aren't done yet. Value types like this are so useful but so tedious to get right and keep right that Kotlin supports them at a language level. Mark the class with the `data` modifier and the compiler will generate the `equals`, `hashCode` and `toString` methods automatically, leaving us with just
-*/

object B {
    //`
    data class Presenter(val id: String, val name: String)
    //`
}

/*-
Ah that's better, and when we add properties to a Presenter we won't have to remember to update the generated methods or find ourselves with hard-to-diagnose bugs.

Now there is so little code, it's easy to pick out a few more differences between Java and Kotlin.

Firstly, the default visibility in Kotlin is public, so `public class Presenter` can just be `class Presenter`.

A> If you are the same sort of Java programmer as we were you may question this language design decision. Our experience working with the language is that it fits well with a more data-oriented design philosophy, where less state has to be hidden. See Chapter TODO.

Secondly, type specifiers in Kotlin come after the identifier, not before it. So instead of `String id` we have `id: String`. This turns out to play nicer with complex nested types and we found that we very quickly didn't notice it at all.

Finally, we have been able to remove the body of the class altogether, so there isn't an empty `{}` pair.

You haven't seen them yet, but we had some tests for our Presenter.
```java
-*/
//#include "java/PresenterTests.java"
/*-
```
Due to the excellent interoperation between Kotlin and Java, these continue to pass with the converted class. Take a moment to think what that implies about the generated class.

Our Java Presenter had explicit `getId` and `getName` methods. These are not in the converted Kotlin, but still our Java can call them. This means that the Kotlin compiler is not only generating the `equals`, `hashCode` and `toString` methods we knew about, but also `getId` and `getName`. `id` and `name` are not fields in Kotlin, they are properties.

A> We'll have a lot to say about properties later, but for now if you think of them as a private field with a public getter you won't often be wrong.

Let's convert the tests themselves to Kotlin
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
As usual, take a few moments to compare before and after.

The first thing that we spot is that the Java fields, `nat` and `duncan` are declared as `val` properties but we haven't had to repeat their type - `Presenter nat = new Presenter(...);`. Kotlin would allow us to say `val nat: Presenter = Presenter(...)` but does not require it here.

A> Properties can be declared with `val` if they don't change, and `var` if they do. Even though the fields were not declared as `final` in the Java; in this case the conversion has been clever enough to see that they are not changed and to convert them to `val`.

Looking at the properties, we see that there is no `new` keyword to construct instances of classes - 'invoking' the class name as a function calls the relevant constructor.

At the end of the line, if you're already used to reading languages other than Java you may not notice the lack of semicolons to terminate statements. They are optional in Kotlin - you can use them to separate statements on a single line, but if the compiler can make sense of code by pretending that there is one on the end of a line it will.

Moving on, we might infer that the Java `void` keyword is replaced in Kotlin by `fun`. Actually though, methods in Kotlin are marked by `fun` and, where they return nothing, do not have to declare a return type.

Looking in the body of the methods, where Java called `nat.getId()`, Kotlin accesses the property `nat.id`. In actual fact Kotlin will call the `getId` that it has generated (or one supplied by a Java class) rather than accessing a field directly - so nothing has really changed except that we can drop the `get` and `()`.

A> There is quite a bit of subtlety to properties. We will keep on returning to this subject.

Stepping away from the Java, and comparing with our previous Kotlin class, Presenter, we see that in the tests the class properties are not declared in the primary constructor.  JUnit requires a default (no argument) constructor, and where properties are not initialised through a constructor (like `nat` and `duncan` here) they are not declared in the primary constructor but rather inside the class body.

## Conclusions

* Convert to Kotlin is handy
* But does not do all the work for you
* Minimum ceremony
* Convenience
* Favour immutability
* Data classes FTW

-*/
