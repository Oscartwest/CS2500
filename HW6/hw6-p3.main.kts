import khoury.CapturedResult
import khoury.EnabledTest
import khoury.captureResults
import khoury.input
import khoury.runEnabledTests
import khoury.testSame

// -----------------------------------------------------------------
// Homework 6, Problem 3
// -----------------------------------------------------------------

// In this problem you'll peel back the covers behind...
// reactConsole!!! You'll first implement the (functional) version
// you've been using, and then experience what an imperative
// version would feel like :)

// TODO 1/2: Use loops and mutation to implement funcReactConsole,
//           which works mostly like what you've been using all
//           semester in the Khoury library...
//
//           a) Start at the (supplied) initial state
//           b) Check if it is a terminal state (via the supplied
//              predicate); if not...
//              i.   Use the supplied rendering function to print to
//                   the screen the rendering of the current state
//                   as its own line.
//              ii.  Use the supplied transition function to change
//                   the current state.
//              iii. Done with this time through the loop!
//           c) Once a terminal state is achieved, use the
//              supplied terminal rendering function to print to
//              the screen the rendering of the terminal state as
//              its own line.
//           d) Return the terminal state.
//
//           Note: the real version uses default arguments, which
//                 we haven't covered, so this version *requires*
//                 that a terminal rendering function is supplied.
//
//           You have been supplied tests that should pass once the
//           function has been completed.
//
//           Note: while this is called funcReactConsole, and feels
//                 like a functional interface to the problem, you
//                 must implement it using loops and mutation!
//

// takes an initial state and loops through user inputs, changing the state after each input, and stopping when the terminal state boolean function is true
fun <T> funcReactConsole(initialState: T, stateToText: (T) -> String, nextState: (T, String) -> T, isTerminalState: (T) -> Boolean, terminalStateToText: (T) -> String): T {
    var state = initialState
    while (!isTerminalState(state)) {
        println(stateToText(state))
        state = nextState(state, input())
    }
    println(terminalStateToText(state))
    return state
}

@EnabledTest
fun testFunReactConsole() {
    testSame(
        captureResults(
            {
                funcReactConsole(
                    initialState = 1,
                    stateToText = { s -> "$s" },
                    nextState = { s, _ -> 2 * s },
                    isTerminalState = { s -> s >= 100 },
                    terminalStateToText = { _ -> "fin." },
                )
            },
            "",
            "",
            "",
            "",
            "",
            "",
            "",
        ),
        CapturedResult(
            128,
            "1", "2", "4", "8", "16", "32", "64", "fin.",
        ),
        "doubling",
    )

    testSame(
        captureResults(
            {
                funcReactConsole(
                    initialState = "start",
                    stateToText = { s -> "$s!" },
                    nextState = { _, kbInput -> kbInput },
                    isTerminalState = { s -> s == "done" },
                    terminalStateToText = { s -> "$s!!!" },
                )
            },
            "howdy",
            "cool",
            "getting old",
            "done",
        ),
        CapturedResult(
            "done",
            "start!",
            "howdy!",
            "cool!",
            "getting old!",
            "done!!!",
        ),
        "done yet?",
    )
}

// TODO 2/2: NOW, we've provided an imperative implementation,
//           whereas you need to implement the classes necessary
//           for the associated tests (which are basically the
//           same as above) to run successfully.

//           Make sure to follow the best practices you've learned
//           for designing classes with mutable state (i.e., these
//           should not be data classes, mutable data should be
//           private, and of course you'll need to test them!).

// member functions necessary for a
// class to utilize the imperative
// reactConsole below
interface IReactStateImp {
    // render the state
    fun toText(): String

    // transition to the next state
    // based upon supplied kb input
    fun transition(kbInput: String)

    // terminal state predicate
    fun isTerminal(): Boolean

    // render terminal states
    fun terminalToText(): String
}

// a class implementing the IReactStateImp interface that uses react console to double a value until it is greater than 128.
class DoublingState(var number: Int) : IReactStateImp {
    override fun toText(): String {
        return number.toString()
    }

    override fun transition(kbInput: String) {
        number *= 2
    }

    override fun isTerminal(): Boolean {
        return number >= 128
    }

    override fun terminalToText(): String {
        return "fin."
    }

    fun getNum(): Int {
        return number
    }
}

@EnabledTest
fun testDoublingState() {
    val doubleState1 = DoublingState(4)

    testSame(doubleState1.toText(), "4", "toText")
    testSame(doubleState1.isTerminal(), false, "isTerminal")
    testSame(doubleState1.terminalToText(), "fin.", "terminalToText")
    testSame(doubleState1.getNum(), 4, "getNum")

    doubleState1.transition("hello")
    testSame(doubleState1.getNum(), 8, "transition")

    doubleState1.transition("")
    doubleState1.transition("")
    doubleState1.transition("")
    doubleState1.transition("")
    testSame(doubleState1.isTerminal(), true, "isTerminal - true")
}

// a class implementing the IReactStateImp interface that uses react console to transition through phrases until the end phrase
class DoneYetState(var phrase: String, val endPhrase: String) : IReactStateImp {
    override fun toText(): String {
        return "$phrase!"
    }

    override fun transition(kbInput: String) {
        phrase = kbInput
    }

    override fun isTerminal(): Boolean {
        return phrase == endPhrase
    }

    override fun terminalToText(): String {
        return "$endPhrase!!!"
    }
}

@EnabledTest
fun testDoneYetState() {
    val doneYet = DoneYetState("lets go", "stop")

    testSame(doneYet.toText(), "lets go!", "toText")
    testSame(doneYet.isTerminal(), false, "isTerminal")
    testSame(doneYet.terminalToText(), "stop!!!", "terminalToText")

    doneYet.transition("hey there")
    testSame(doneYet.toText(), "hey there!", "transition")

    doneYet.transition("stop")
    testSame(doneYet.isTerminal(), true, "isTerminal - true")
}

// implements reactConsole imperatively
// (using an object-oriented state)
fun impReactConsole(state: IReactStateImp) {
    while (!state.isTerminal()) {
        println(state.toText())

        state.transition(input())
    }
    println(state.terminalToText())
}

@EnabledTest
fun testImpReactConsole() {
    // creates the initial state
    // for the doubling program
    val doubleState = DoublingState(1)

    // tests (primarily) the printed program output
    testSame(
        captureResults(
            { impReactConsole(doubleState) },
            "",
            "",
            "",
            "",
            "",
            "",
            "",
        ),
        CapturedResult(
            Unit,
            "1", "2", "4", "8", "16", "32", "64", "fin.",
        ),
        "doubling: I/O",
    )

    // tests (primarily) the final program state
    testSame(
        doubleState.getNum(),
        128,
        "doubling: final state",
    )

    // creates the initial state
    // for the done-yet program
    // (with both starting phrase
    // and phrase that signals
    // the end)
    val startPhrase = "start"
    val exitPhrase = "done"
    val doneYetState = DoneYetState(startPhrase, exitPhrase)

    // tests the program printed output (as dictated
    // by keyboard input)
    testSame(
        captureResults(
            { impReactConsole(doneYetState) },
            "howdy",
            "cool",
            "getting old",
            exitPhrase,
        ),
        CapturedResult(
            Unit,
            "$startPhrase!",
            "howdy!",
            "cool!",
            "getting old!",
            "$exitPhrase!!!",
        ),
        "done yet?: I/O",
    )
}

fun main() {
    runEnabledTests(this)
}

main()
