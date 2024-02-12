import khoury.EnabledTest
import khoury.runEnabledTests
import khoury.testSame

// -----------------------------------------------------------------
// Homework 2, Problem 3
// -----------------------------------------------------------------

// We are making nametags for a (magical) student event, and want
// to make sure they have enough space for all the letters in each
// name. We are also formatting the nametags so that they have a
// consistent format: Last, First Middle - this way the teachers
// can accurately praise/critique their students.
//
// TODO 1/1: Given the data types and examples below, design the
//           function numCharsNeeded that takes a magical pair and
//           returns the number of characters that would be
//           necessary to represent the longer student's formatted
//           name.
//
//           For instance, if Hermione and Ron are paired, her
//           formatted name is "Granger, Hermione Jean" (22
//           characters) and his is "Weasley, Ron Bilius" (19
//           characters), and so the function should return 22.
//
//           Note the nested data types, and so duplicated work -
//           let type-driven development lead you to an effective
//           decomposition of well-designed functions :)
//

// represents a person's first/middle/last names
data class Name(val first: String, val middle: String, val last: String)

val harryJPotter = Name("Harry", "James", "Potter")
val hermioneJeanGranger = Name("Hermione", "Jean", "Granger")
val ronBiliusWeasley = Name("Ron", "Bilius", "Weasley")

// represents a pairing of two names
data class MagicPair(val p1: Name, val p2: Name)

val magicHarryRon = MagicPair(harryJPotter, ronBiliusWeasley)
val magicHarryHermione = MagicPair(harryJPotter, hermioneJeanGranger)
val magicHermioneRon = MagicPair(hermioneJeanGranger, ronBiliusWeasley)

// gets the number of characters needed on a properly formated name tag given a pair of names
fun numCharsNeeded(pair: MagicPair): Int {
    val p1name: String = (pair.p1.last + ", " + pair.p1.first + " " + pair.p1.middle)
    val p2name: String = (pair.p2.last + ", " + pair.p2.first + " " + pair.p2.middle)
    if (p1name.length > p2name.length) {
        return p1name.length
    } else {
        return p2name.length
    }
}

// tests the function numCharsNeeded
@EnabledTest
fun testCharsNeeded() {
    testSame((::numCharsNeeded)(magicHarryRon), 19, "Harry/Ron")
    testSame((::numCharsNeeded)(magicHarryHermione), 22, "Harry/Hermione")
    testSame((::numCharsNeeded)(magicHermioneRon), 22, "Hermione/Ron")
}
fun main() {
    runEnabledTests(this)
}

main()
