# Topics to cover

## Book structure

* Part 1: intro
* Part 2: worked example "in the small"
* Part 3: Strategies for working with Kotlin and Java in a team and with a production codebase (testing, etc.)
* Part 4: evolving the architecture to better take advantage of Kotlin (compositional, immutability, OO shell / functional core)


## Small details...

* The "grain" of Kotlin:
** Immutable data
** Functions rather than classes
** Deliberate polymorphism
** Explicit control flow

* Kotlin supports "beans" style Java well, but you don't get a lot of the benefits at larger scales.  Modern Java lends itself well to conversion to idiomatic Kotlin that takes advantage of Kotlin's default immutability, better type system and the safety that provides.

* ~~Adding a Kotlin build to a Java project~~

* Option<T> -> nullable T? -- showing how to Java and Kotlin handle null differently
** The problem with null in Java
** Nullable annotations and their limitations (digression or sidebar?)
** Optional
** Null is part of Kotlin's type system: type safety is null safety

* Properties vs fields/get/set

* ~~value classes vs data class~~

* Collections and compiler magic
** Read-only vs Read/write interfaces

* Java v Kotlin Classes in more detail...
** Constructors, Properties, Methods
** Java style (classes with methods) vs Kotlin style (classes, freestanding functions or extension methods, class methods for polymorphism)

* Statics vs companion objects, and annotating Kotlin code to play well with existing Java (@JvmStatic)

* Extension methods
** Extension functions on core library types
** how they play nicely with null
** define them for different domains in the app
** Extension functions on nullable types

* Scope management functions (let, apply etc)

* Calling Java from Kotlin and vice versa

* Compilation units, visibility modifiers, free-standing definitions

* Lambdas, destructuring

* Typealiases

* Refactoring into a DSL rather than designing one from scratch


## Larger scale gubbins

Later chapters will build on this core - they will be about more high-level refactorings.

* Error handling - checked exceptions to result monad
** The background of error handling facilities inherited from Java: exceptions.
** Options/nulls
** monadic error handling

* Data Pipelines
** Composing algorithms from sequence of primitive transformations
** Iterable Collections vs Sequences vs Streams
** Limitations of Streams API -- it doesn't let you abstract transformations of entire streams in the same way as transformations of values within a stream. E.g. you cannot add new, higher-level methods to Streams
** Using Kotlin extension methods to refactor pipeline stages: transformations of streams and of values can be treated in the same way, leading to much more readable pipelines.

* Input and Output
** The problem with IO
** Moving IO to the edge of our software
** Functional Design (refactoring from the procedural style common in enterprise Java systems to a functional style)
** Functional core, imperative shell
** Referential transparency

* Compositionality

* Data driven design [???]
