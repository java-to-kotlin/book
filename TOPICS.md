# Book Topics

Less “Java language is like this, kotlin is like this…” but “here’s how you would design this kind of thing in Java, here’s how you’d approach the same kind of thing in Kotlin, and here’s how to go from one to the other”.

Evolving design and architecture to better take advantage of Kotlin's affordances.


## Book structure

* Introduction
  - Who are we?
  - Who do we think *you* are?
  - Why Kotlin? (concision, consistency, convenience, null safety) \[can we think of another "c" word that covers null safety?\]
  - How does Kotlin support Java programming style? (opinionated history)
  - Kotlin's affordances (aka the "grain" of the language)
    - Immutable data
    - Functions rather than classes
    - Deliberate polymorphism (e.g. choose where classes are polymorphic)
    - Explicit over implicit (e.g. explicit control flow, explicit coercion)
    - Type safe composition over reflection
    - Compiler magic to support Java styles and standard APIs 
  - An overview of the application from which we have taken the worked examples
  - Making the application multilingual
  - Where should we start refactoring to Kotlin?

* Part: Values and optionality
  - Value classes
  - Nullability
    - The problem with null in Java
    - Nullable annotations and their limitations (digression or sidebar?)
    - Optional -- doesn't actually protect against NPEs because the Optional itself can be null -- but never is in practice.  (show static warnings?)

* Part: Data Pipelines
  - Collections and Kotlin's compiler magic
  - For loops and mutation to collection pipelines.
  - Streams vs Kotlin Iterables and sequences: extension functions to abstract multiple processing steps
  - Composing algorithms from sequence of primitive transformations
  - Iterable Collections vs Sequences vs Streams
  - Limitations of Streams API -- it doesn't let you abstract transformations of entire streams in the same way as transformations of values within a stream. E.g. you cannot add new, higher-level methods to Streams
  - Using Kotlin extension methods to refactor pipeline stages: transformations of streams and of values can be treated in the same way, leading to much more readable pipelines.

* Part: Extension functions? What else are they good for?
  - Kotlin style: deliberate polymorphism (e.g. classes and functions are final by default)
  - Extending third-party types
  - Scope management functions (let, apply etc) (**A SIDE BAR MAYBE?**)
  - API design by adding extension functions for existing types rather than defining new types (I think this is a big enough topic to be either a long digression or its own chapter).
    - e.g. the "curried object" pattern is less necessary
  - extension functions play well with nullability
  - separating the "platonics" from "pragmatics" - extension functions cross the domains of the application
  - relation to hexagon architecture: extension functions in adapter domains

* Part: From Builders to EDSLs
  - Compositional DSL from Java to Kltoin
  - Why so many builders in Java? 
  - The alternatives in Kotlin:
    - Data classes and copy
    - Vars and distinct mutable & immutable interfaces
    - Factory function passes mutable interface to extension lambda
  - Translating builder style to Kotlin
  - Refactoring to small DSLs that compose well -- avoid designing a big, inflexible language up front.

* Part: Error handling
  - Why are exceptions unchecked in Kotlin?  What problems does that cause?
  - Error handling options -- exceptions, null, monads (don't use the "M" word!) -- and when they make sense
  - Refactoring from exceptions to monads

* Part: I/O
  - The problem with IO
  - Kotlin is not a pure FP language... but we can get much of the benefit anyway
  - separating total functions from partial and impure functions
  - Moving IO to the edge of our software
  - Functional core / imperative shell (the "inverted shit sandwich" pattern)

* Part: constructing the system
  - Compilation units, visibility modifiers, free-standing definitions
  - Reflection magic to compositionality guided by types (what is "compositionality" compared to "composition"?)

