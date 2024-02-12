import khoury.CapturedResult
import khoury.EnabledTest
import khoury.captureResults
import khoury.input
import khoury.runEnabledTests
import khoury.testSame
// -----------------------------------------------------------------
// Homework 2, Problem 2
// -----------------------------------------------------------------

// TODO 1/1: Finish designing the function wakeupTime that asks at
//           the console what hour a person likes to wake up in
//           the morning. Depending on their response, your
//           function provides a particular response. You should
//           NOT use reactConsole for this problem.
//
//           To help, you have been supplied a set of tests --
//           uncomment these and once your function passes, you
//           should be in good shape!
//
//           If you are having trouble, try calling your function
//           from main and debug!
//

val wakeupPrompt = "What hour in the morning (1-11) do you like to wakeup?"
val wakeupNotANumber = "You did not enter a number :("
val wakeupNotInRange = "You did not enter an hour from 1-11am"
val wakeupEarly = "Before 8am? Early bird catches the worm!"
val wakeupOther = "8am or later? Coffee time!"

// prints different statements depending on console input
fun wakeupTime() {
    println(wakeupPrompt)
    var answer: String = input()
    try {
        when {
            answer.toInt() >= 1 && answer.toInt() < 8 -> println(wakeupEarly)
            answer.toInt() >= 8 && answer.toInt() <= 11 -> println(wakeupOther)
            answer.toInt() > 11 || answer.toInt() < 1 -> println(wakeupNotInRange)
        }
    } catch (e: Exception) {
        println(wakeupNotANumber)
    }
}

@EnabledTest
fun testWakeupTime() {
    // helps to test, given what is typed at the console
    // and what the expected output should be
    fun testHelp(
        consoleIn: String,
        expectedOut: String,
    ) {
        testSame(
            captureResults(::wakeupTime, consoleIn),
            CapturedResult(
                Unit,
                wakeupPrompt,
                expectedOut,
            ),
            consoleIn,
        )
    }

    testHelp("howdy", wakeupNotANumber)
    testHelp("0", wakeupNotInRange)
    testHelp("12", wakeupNotInRange)
    testHelp("5", wakeupEarly)
    testHelp("8", wakeupOther)
    testHelp("11", wakeupOther)
}

fun main() {
    runEnabledTests(this)
}

main()
