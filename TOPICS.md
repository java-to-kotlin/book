# Topics to cover

## Book structure

* Introduction
  - Who are we?
  - Who do we think *you* are?
  - Why Kotlin? (concision, consistency, convenience, null safety) \[can we think of another "c" word that covers null safety?\]
  - How does Kotlin support Java programming style? (opinionated history)
  - Kotlin's affordances (aka the "grain" of the language)
  - An overview of the worked example(s)
  - Where should we start refactoring to Kotlin?
* Part 2: worked examples "in the small"
  - Value classes
  - Nullability
  - Collections and Kotlin's compiler magic
  - For loops and mutation to collection pipelines.
  - Streams vs Kotlin Iterables and sequences: extension functions to abstract multiple processing steps 
  - Modules and visibility modifiers
  - ???
* Part 3: evolving architecture to better take advantage of Kotlin's affordances
 - Extension functions: platonic ideal and extension functions in different "domains"
 - Mutability to immutability
 - Reflection to type safe composition
 - "DSLs"
 - error handling
 - Move effects to the edge: OO shell / functional core



# Uncategorised so far...

## Small details...

* The "grain" of Kotlin:
  - Immutable data
  - Functions rather than classes
  - Deliberate polymorphism (e.g. choose where classes are polymorphic)
  - Explicit control flow

* Interop
** Kotlin supports "beans" style Java well, but you don't get a lot of the benefits at larger scales.  Modern Java lends itself well to conversion to idiomatic Kotlin that takes advantage of Kotlin's default immutability, better type system and the safety that provides.

* ~~Adding a Kotlin build to a Java project~~

* Option<T> -> nullable T? -- showing how to Java and Kotlin handle null differently
  - The problem with null in Java
  - Nullable annotations and their limitations (digression or sidebar?)
  - Optional -- doesn't actually protect against NPEs because the Optional itself can be null -- but never is in practice.
  - ~~Null is part of Kotlin's type system: type safety is null safety~~

* ~~Properties vs fields/get/set~~

* ~~value classes vs data class~~

* Collections and compiler magic (need more about the compiler magic)
** Read-only vs Read/write interfaces

* Java v Kotlin Classes in more detail...
  - Constructors, Properties, Methods
  - Java style (classes with methods) vs Kotlin style (classes, freestanding functions or extension methods, class methods for polymorphism)

* Statics vs companion objects

* Annotating Kotlin code to play well with existing Java (@JvmStatic, @JvmName, @JvmField, etc)

* Extension methods
  - Extension functions on core library types
  - how they play nicely with null
  - define them for different domains in the app
** ~~Extension functions on nullable types~~

* Scope management functions (let, apply etc)

* Calling Java from Kotlin and vice versa

* Compilation units, visibility modifiers, free-standing definitions

* Lambdas, destructuring

* Typealiases

* Refactoring into a DSL rather than designing one from scratch


## Larger scale gubbins

Later chapters will build on this core - they will be about more high-level refactorings, or architectural styles that inform the direction of continual refactoring.

* Extension functions
  - API design by adding extension functions rather than defining new types (I think this is a big enough topic to be either a long digression or its own chapter).
  - Define extension functions for different domains in the app

* DSLs: (build upon extension functions chapter(s))
  - refactor to DSLs, don't design up front
  - Small notations that can easily be combined in preference to large DSLs that can be unwieldy  
  - Compositional (immutable data, functions, function application & composition) vs builder-style DSLs (extension methods on mutable objects)
    + prefer the former until the latter is unavoidable
  - Refactoring Java approaches to DSL-like code into Kotlin
    + statics class members in Java to top-level declarations, extension functions, infix functions, etc.
    + use `apply` instead of builders
    + separating mutable and read-only interfaces of mutable classes
  - Introduce extension blocks and syntactic sugar in wrappers to build DSLs

* Error handling - checked exceptions to result monad
  - The background of error handling facilities inherited from Java: exceptions.
  - Options/nulls
  - monadic error handling

* Data Pipelines
  - Composing algorithms from sequence of primitive transformations
  - Iterable Collections vs Sequences vs Streams
  - Limitations of Streams API -- it doesn't let you abstract transformations of entire streams in the same way as transformations of values within a stream. E.g. you cannot add new, higher-level methods to Streams
  - Using Kotlin extension methods to refactor pipeline stages: transformations of streams and of values can be treated in the same way, leading to much more readable pipelines.

* Input and Output
  - The problem with IO
  - Moving IO to the edge of our software
  - Functional Design (refactoring from the procedural style common in enterprise Java systems to a functional style)
  - Functional core, imperative shell
  - Referential transparency

* Compositionality

* Data driven design [???]

## Other Random Things

fun <T> hole(): T = TODO()
Type-driven design
