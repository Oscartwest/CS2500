import khoury.EnabledTest
import khoury.runEnabledTests
import khoury.testSame

// -----------------------------------------------------------------
// Homework 5, Problem 4
// -----------------------------------------------------------------

// In this problem you'll practice writing a function based upon an
// interface - so it'll work equally well with different data
// types, so long as they implement the interface's requirements.

// TODO 1/1: Consider the following interface...
//

interface INamedThing {
    fun getName(): String
}

//           Your task is to finish designing the function
//           renderNamedThings that produces a single, comma-
//           separated string from a list of items that each
//           implement INamedThing.
//
//           You have been supplied tests that should pass once the
//           function has been completed.
//
//           Hint: joinToString is your friend :)
//

data class NamedInt(val num: Int) : INamedThing {
    // name is the number
    override fun getName(): String = "$num"
}

data class NamedPerson(val first: String, val last: String) : INamedThing {
    // name is combo of first/last
    override fun getName(): String = "$first $last"
}

data class SamedNamedInt(val num: Int) : INamedThing {
    // name is always the same
    override fun getName(): String = "Inigo Montoya"
}

// returns a formatted string of all the names in a list of INamedThings
fun renderNamedThings(list: List<INamedThing>): String {
    // helper function that converts allows the getName property to be used for the .map abstraction
    fun nameGetter(thing: INamedThing): String {
        return thing.getName()
    }
    return list.map(::nameGetter).joinToString(", ")
}

@EnabledTest
fun testRenderNamedThings() {
    testSame(
        renderNamedThings(emptyList<INamedThing>()),
        "",
        "empty",
    )

    testSame(
        renderNamedThings(
            listOf(
                NamedInt(0),
                NamedInt(42),
                NamedPerson("Harry", "Potter"),
                NamedPerson("Hermione", "Granger"),
                SamedNamedInt(42),
                SamedNamedInt(0),
            ),
        ),
        "0, 42, Harry Potter, Hermione Granger, Inigo Montoya, Inigo Montoya",
        "non-empty",
    )
}

fun main() {
    runEnabledTests(this)
}

main()
