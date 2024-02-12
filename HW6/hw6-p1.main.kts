import khoury.EnabledTest
import khoury.runEnabledTests
import khoury.testSame

// -----------------------------------------------------------------
// Homework 6, Problem 1
// -----------------------------------------------------------------

// In this problem, you'll practice with looping and mutation to
// implement a form of the functional `map` function to actually
// change the elements of a supplied list (instead of returning
// a new list).

// TODO 1/1: Design the function myInplaceMap, which transforms the
//           elements of a supplied list using a supplied function.
//
//           For example, if the function was supplied [1, 2, 3] as
//           a mutable list, as well as a function that doubles a
//           supplied number, the supplied list would be [2, 4, 6]
//           after the function completed (note that the function
//           itself should not return any value).
//
//           Make sure to sufficiently test this function,
//           including empty vs lists that have elements, as well
//           as differing types.
//

// takes in a mutable list and changes the values of the list based off of the function provided
fun <T> myInplaceMap(list: MutableList<T>, func: (T) -> T) {
    for (index in list.indices) {
        list[index] = func(list[index])
    }
}

@EnabledTest
fun testMyInplaceMap() {
    val emptyList = mutableListOf<Int>()
    myInplaceMap(emptyList, { it + 1 })
    testSame(emptyList, mutableListOf<Int>())

    val list = mutableListOf(1, 2, 3)
    myInplaceMap(list, { it * 2 })
    testSame(list, mutableListOf(2, 4, 6))

    val list2 = mutableListOf("happy", "birthday", "to", "you")
    myInplaceMap(list2, { it.first().toString() })
    testSame(list2, mutableListOf("h", "b", "t", "y"))
}

fun main() {
    runEnabledTests(this)
}

main()
