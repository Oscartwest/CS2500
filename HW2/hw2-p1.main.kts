import khoury.EnabledTest
import khoury.runEnabledTests
import khoury.testSame
// -----------------------------------------------------------------
// Homework 2, Problem 1
// -----------------------------------------------------------------

// TODO 1/1: Design the predicate startsWithY that determines if
//           the supplied string starts with the letter "y"
//           (either upper or lowercase).
//
//           Hints:
//            - The string.startsWith(prefix) function will help
//              evaluate the prefix (even if the string is too
//              short).
//            - The string.lowercase/uppercase() functions help
//              you not worry about case.
//            - Remember that "designing" a function means to
//              document and test it!
//

// checks if a string starts with the letter Y
fun startsWithY(s: String): Boolean {
    return (s.uppercase().startsWith("Y"))
}

// tests start with Y
@EnabledTest
fun testStartsWithY() {
    testSame((::startsWithY)("yes"), true, "yes")
    testSame((::startsWithY)("no"), false, "no")
    testSame((::startsWithY)("YES"), true, "YES")
}

fun main() {
    runEnabledTests(this)
}

main()
