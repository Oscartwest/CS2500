import khoury.EnabledTest
import khoury.runEnabledTests
import khoury.testSame

// TODO 1/6: Design the function computeFrequencies 
//           that produces a list of counts for a 
//           supplied list.

// Steps...
// 1. Design a type that can associate an element 
//    in the list with a count
// 2. Test!
// 3. In the function, list.distinct() helps get 
//    all distinct values in the list

// @EnabledTest
// fun testComputeFrequencies() {
//     testSame(
//         computeFrequencies<Int>(listOf()),
//         ???,
//         "empty"
//     )

//     testSame(
//         computeFrequencies(listOf("a", "b", "c", "b", "c", "c")),
//         ???,
//         "non-empty"
//     )
// }


// TODO 2/6: Design the function numPalindromes that returns the 
//           number of supplied texts are the same forward and 
//           backwards.

fun numPalindromes(list: List<String>): Int {
    return list.filter {
        thing -> thing.equals(thing.reversed())
    }.size
}

@EnabledTest
fun testNumPalindromes() {
    testSame(numPalindromes(emptyList()), 0, "empty")
    testSame(numPalindromes(listOf("racecar", "tacocat", "poop", "happiness")), 3, "3 palindromes")
}


// TODO 3/6: Design the function allBiggerThan that determines if 
//           every number in a list is bigger than a supplied 
//           number.

// @EnabledTest
// fun testAllBiggerThan() {
//     testSame(
//         allBiggerThan(listOf(8, 6, 7, 5, 3, 0, 9), -1),
//         true,
//         "easy"
//     )

//     testSame(
//         allBiggerThan(listOf(8, 6, 7, 5, 3, 0, 9), 9),
//         false,
//         "equal high"
//     )

//     testSame(
//         allBiggerThan(listOf(8, 6, 7, 5, 3, 0, 9), 10),
//         false,
//         "none"
//     )

//     testSame(
//         allBiggerThan(listOf(8, 6, 7, 5, 3, 0, 9), 0),
//         false,
//         "equal low"
//     )
// }


// TODO 4/6: Design the function negate that negates a predicate.

// represents a yes/no function on a type
typealias Predicate<T> = (T) -> Boolean

fun isEven(n: Int) = n % 2 == 0
fun isEmpty(s: String) = s.isEmpty()

fun <T> negate(function: Predicate<T>): Predicate<T> {
    return {
        thing -> !function(thing)
    }
}


@EnabledTest
fun testNegate() {
    val isOdd = negate(::isEven)
    val hasChars = negate(::isEmpty)

    listOf(1, 3, 5).forEach {
        testSame(
            isOdd(it),
            true,
            "$it"
        )
    }

    listOf(0, 2, 4, 6).forEach {
        testSame(
            isOdd(it),
            false,
            "$it"
        )
    }

    testSame(
        hasChars(""),
        false,
        "empty string"
    )

    listOf("a", "howdy").forEach {
        testSame(
            hasChars(it),
            true,
            it
        )
    }
}


// TODO 5/6: Design the function compose, that returns (for two 
//           functions, f1 and f2), a function that produces 
//           f2(f1(x)).

// @EnabledTest
// fun testCompose() {
//     fun add1(n: Int) = n + 1
//     fun sqr(n: Int) = n * n

//     val add2 = compose(::add1, ::add1)
//     val sqrAdd1 = compose(::add1, ::sqr)

//     testSame(
//         add2(0),
//         2,
//         "add1(add1(0))"
//     )

//     testSame(
//         sqrAdd1(2),
//         9,
//         "sqr(add1(2))"
//     )
// }


// TODO 6/6: Design the function checklistToPredicate that accepts 
//           a Checklist (designed below) and returns a single 
//           predicate that only returns true if all predicates in 
//           the checklist return true.

// represents a list of predicates for a particular type
typealias Checklist<T> = List<Predicate<T>>

// requirements of a positive, even integer
val numReqs: Checklist<Int> = listOf(
    { it > 0 },
    { it % 2 == 0 }
)

// requirements of a non-empty, PALINDROME
val wordReqs: Checklist<String> = listOf(
    { !it.isEmpty() },
    { it == it.reversed() },
    { it == it.uppercase() }
)

//this is truly ridiculous
fun <T> checklistToPredicate(check: Checklist<T>): Predicate<T> {
    return {
        thing -> check.all {
            it(thing)
        }
    }
}

@EnabledTest
fun testChecklistToPredicate() {
    val predPosEven = checklistToPredicate(numReqs)
    val predCapsPalindrome = checklistToPredicate(wordReqs)

    listOf(-1, -2, 0, 1, 51).forEach {
        testSame(
            predPosEven(it),
            false,
            "$it"
        )
    }

    listOf(2, 42).forEach {
        testSame(
            predPosEven(it),
            true,
            "$it"
        )
    }

    listOf("", "a", "aba", "AB", "aA", "Bb").forEach {
        testSame(
            predCapsPalindrome(it),
            false,
            it
        )
    }

    listOf("A", "ABA", "AA", "BB").forEach {
        testSame(
            predCapsPalindrome(it),
            true,
            it
        )
    }
}



fun main() {
}

runEnabledTests(this)
main()
