package chapter1

import chapter1.java.Presenter
import chapter1.java.Slots
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.util.*
import java.util.Arrays.asList

/*-
Looking in the codebase in which we found Presenter, we find another class ripe for conversion to a data class.

```java
-*/
//#include "java/Session.java"

//#include "java/SessionTests.java"
/*-
```
Session is another Value Object, with tedious `equals`, `hashCode` and `toString` methods, but this time it already has copy methods, the `withPresenters` etc.

`subtitleOrNull` is both named and annotated to let us know that it may not be present, so we had better check for null before using the subtitle. Some shops would have had a getter returning an Optional<String>, but in this case it seems that we took advantage of the class' immutability to leave out the getters in favour of direct public field access.

Finally note that we've chosen to defensively copy the list of presenters passed into the constructor into an `unmodifiableList` in order to prevent clients mutating the list and breaking our value semantics.

For such a simple class there are a lot of hoops to jump through. Let's convert to Kotlin to see what we get.
-*/
object F {
    //`
    class Session(val title: String, val subtitleOrNull: String?, val slots: Slots, presenters: List<Presenter>) {
        val presenters: List<Presenter>

        init {
            this.presenters = Collections.unmodifiableList(ArrayList(presenters))
        }

        constructor(title: String, subtitle: String?, slots: Slots, vararg presenters: Presenter) : this(
            title,
            subtitle,
            slots,
            asList<Presenter>(*presenters)
        ) {
        }

        override fun equals(o: Any?): Boolean {
            if (this === o) return true
            if (o == null || javaClass != o.javaClass) return false
            val session = o as Session?
            return title == session!!.title &&
                subtitleOrNull == session.subtitleOrNull &&
                slots == session.slots &&
                presenters == session.presenters
        }

        override fun hashCode(): Int {
            return Objects.hash(title, subtitleOrNull, slots, presenters)
        }

        override fun toString(): String {
            return "Session{" +
                "title='" + title + '\''.toString() +
                ", subtitle='" + subtitleOrNull + '\''.toString() +
                ", slots=" + slots +
                ", presenters=" + presenters +
                '}'.toString()
        }

        fun withPresenters(newLineUp: List<Presenter>): Session {
            return Session(title, subtitleOrNull, slots, newLineUp)
        }

        fun withTitle(newTitle: String): Session {
            return Session(newTitle, subtitleOrNull, slots, presenters)
        }

        fun withSubtitle(newSubtitle: String?): Session {
            return Session(title, newSubtitle, slots, presenters)
        }
    }
    //`
}
/*-
At the time of writing IntelliJ's convert to Kotlin doesn't do a great job on this code, but we can learn a lot by tidying, so let's work our way though it.

The first new thing is that `subtitleOrNull` has the type String?. This is Kotlin for a String that may be null. The conversion wasn't clever enough to look at the name, but instead took heed of the @Nullable annotation. We'll look at the ramifications of nullability in the typesystem later.

Next note that `presenters` is a constructor parameter that isn't declared as `val`, but later there is a `presenters` property, and that it is initialised in an `init` block, which has access to the constructor parameters by name.

That's all a bit odd - the conversion seems to have been confused by our defensive copying of `presenters`. `Alt-Enter` on the `val` gives the option to 'Join declaration and assignment'
-*/

object G {
    class Session(val title: String, val subtitleOrNull: String?, val slots: Slots, presenters: List<Presenter>) {
        //`
        val presenters: List<Presenter> = Collections.unmodifiableList(ArrayList(presenters))
        //`
    }
}

/*-
and then we can remove that vestigial ArrayList construction as well.
-*/

object H {
    class Session(val title: String, val subtitleOrNull: String?, val slots: Slots, presenters: List<Presenter>) {
        //`
        val presenters: List<Presenter> = Collections.unmodifiableList(presenters)
        //`
    }
}

/*-
Moving on we see that Kotlin allows for other constructors to be declared with the `constructor` keyword. Here we have a single secondary constructor which takes its presenters as `vararg`s for convenience, especially in test code, and converts them to a List to present to the primary constructor.
-*/
object I {
    class Session(val title: String, val subtitleOrNull: String?, val slots: Slots, presenters: List<Presenter>) {
        val presenters: List<Presenter> = Collections.unmodifiableList(presenters)

        //`
        constructor(title: String, subtitle: String?, slots: Slots, vararg presenters: Presenter) :
            this(
                title,
                subtitle,
                slots,
                asList<Presenter>(*presenters)
            ) {
        }
        //`
    }
}

/*-
That `asList<Presenter>(*presenters)` is too ugly to live - it's invoking Java's `Arrays.asList(T... a).` A better Kotlin expression would be `toList()` so we will substitute that, and at the same time remove those useless secondary constructor braces.
-*/
object J {
    //`
    class Session(val title: String, val subtitleOrNull: String?, val slots: Slots, presenters: List<Presenter>) {
        val presenters: List<Presenter> = Collections.unmodifiableList(presenters)

        constructor(title: String, subtitle: String?, slots: Slots, vararg presenters: Presenter) :
            this(
                title,
                subtitle,
                slots,
                presenters.toList()
            )
    }

    // equals etc
    //`
}

/*-
A subtlety of the conversion is that while the original Java had `presenters` as List<Presenter>, the Kotlin has them as List<Presenter>. OK, maybe that explanation is itself too subtle, because in the Kotlin case the List is not `java.util.List` but `kotlin.collections.List`. Whereas the Java interface has methods to mutate the list (add and remove elements, replace them, sort in place etc), the Kotlin version does not.

The Kotlin list is not truly immutable, but instead read only. If we ignore *this* subtlety, and you generally should in your Kotlin code, then we can take it on trust that the `presenters` list passed to the primary constructor will not be changed, so that we don't have to make a defensive copy. In that case the property can be moved into the primary constructor.
-*/
object K {
    //`
    class Session(
        val title: String,
        val subtitleOrNull: String?,
        val slots: Slots,
        val presenters: List<Presenter>
    ) {
        constructor(title: String, subtitle: String?, slots: Slots, vararg presenters: Presenter) :
            this(
                title,
                subtitle,
                slots,
                presenters.toList()
            )
    }

    // equals etc
    //`
}

/*-
Now that all primary constructor parameters are `val`s (or `var`s) our class is eligible for conversion to a data class, allowing us to remove the boilerplate methods and implement the Java copy methods with the Kotlin copy method.
-*/
object L {
    //`
    data class Session(
        val title: String,
        val subtitleOrNull: String?,
        val slots: Slots,
        val presenters: List<Presenter>
    ) {
        constructor(title: String, subtitle: String?, slots: Slots, vararg presenters: Presenter) :
            this(
                title,
                subtitle,
                slots,
                presenters.toList()
            )

        fun withPresenters(newLineUp: List<Presenter>): Session {
            return this.copy(presenters = newLineUp)
        }

        fun withTitle(newTitle: String): Session {
            return this.copy(title = newTitle)
        }

        fun withSubtitle(newSubtitle: String?): Session {
            return this.copy(subtitleOrNull = newSubtitle)
        }
    }
    //`
}