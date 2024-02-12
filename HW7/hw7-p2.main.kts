import khoury.EnabledTest
import khoury.runEnabledTests
import khoury.testSame

// -----------------------------------------------------------------
// Homework 7, Problem 2
// -----------------------------------------------------------------

// In this problem you'll implement an algorithm in natural-
// language processing (NLP), which helps you reason about how
// "close" two pieces of text are from one another, by computing
// their "distance" as the minimum number of single-character
// changes (e.g., adding a character, removing one, or
// substituting) required to change one into another.
//
// TODO 1/1: Finish designing the function levenshteinDistance
//           (https://en.wikipedia.org/wiki/Levenshtein_distance),
//           which computes the distance between two strings.
//
//           Notes:
//           - In the Wikipedia article, the "Definition" is really
//             what you want to translate to Kotlin; this is a
//             common task in software development - translating a
//             theoretical/mathematical description of an approach
//             into code for your system. You are encouraged to
//             make your code look as similar as possible to this
//             description!
//           - In class we learned some best practices about the
//             use of recursion (vs iteration); in this case, you
//             can assume we'll apply this to relatively short
//             texts, which makes a recursive approach reasonable.
//           - You have been supplied some tests to help make sure
//             you got it right. Be sure to make sure you
//             understand the metric and tests before you start
//             coding!
//

// finds the minimum number of single-character edits (insertions, deletions or substitutions) required to change one word into the other
fun levenshteinDistance(a: String, b: String): Int {
    when {
        a == "" -> return b.length
        b == "" -> return a.length
        a.first() == b.first() -> return levenshteinDistance(a.substring(1), b.substring(1))
        else -> return (1 + minOf(levenshteinDistance(a.substring(1), b.substring(1)), levenshteinDistance(a.substring(1), b), levenshteinDistance(a, b.substring(1))))
    }
}

@EnabledTest
fun testLevenshteinDistance() {
    testSame(
        levenshteinDistance("", "howdy"),
        5,
        "'', 'howdy'",
    )

    testSame(
        levenshteinDistance("howdy", ""),
        5,
        "'howdy', ''",
    )

    testSame(
        levenshteinDistance("howdy", "howdy"),
        0,
        "'howdy', 'howdy'",
    )

    testSame(
        levenshteinDistance("kitten", "sitting"),
        3,
        "'kitten', 'sitting'",
    )

    testSame(
        levenshteinDistance("sitting", "kitten"),
        3,
        "'sitting', 'kitten'",
    )
}

fun main() {
    runEnabledTests(this)
}

main()
