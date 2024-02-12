import khoury.EnabledTest
import khoury.runEnabledTests
import khoury.testSame
// -----------------------------------------------------------------
// Homework 3, Problem 2
// -----------------------------------------------------------------

// TODO 1/4: Design the data type FlashCard to represent a single
//           flash card. You should be able to represent the text
//           prompt on the front of the card as well as the text
//           answer on the back. Include at least 3 example cards
//           (which will come in handy later for tests!).
//

// a card that contains two strings
data class FlashCard(val front: String, val back: String)

val card1: FlashCard = FlashCard("2+2", "4")
val card2: FlashCard = FlashCard("8 * 6", "48")
val card3: FlashCard = FlashCard("2-2", "0")

// TODO 2/4: Design the data type Deck to represent a deck of
//           flash cards. The deck should have a name, as well
//           as a sequence of flash cards.
//
//           Include at least 2 example decks based upon the
//           card examples above.
//

// contains a list of flashcards and a name
data class Deck(val name: String, val cardList: List<FlashCard>)

val deck1: Deck = Deck("deck1", listOf(card1, card3))
val deck2: Deck = Deck("deck2", listOf(card1, card2))

// TODO 3/4: Design the predicate areAllOneWordAnswers that
//           determines if the backs of all the cards in a deck
//           are a single word (i.e., have no spaces, which
//           includes a card with a blank back).
//
//           Hint: hidden in the name of this function is a
//                 reminder of a useful list function to use :)
//

// checks if all cards in deck are one word
fun areAllOneWordAnswers(deck: Deck): Boolean {
    for (index in 0..(deck.cardList.count() - 1)) {
        if (deck.cardList[index].front.contains(" ")) {
            return false
        }
        if (deck.cardList[index].back.contains(" ")) {
            return false
        }
    }
    return true
}

// A couple potentially helpful examples for tests
// val fcEmptyBack = FlashCard("Front", "")
// val fcLongBack = FlashCard("Front", "Long answer")

// TODO 4/4: Design the predicate anyContainsPhrase that determines
//           if any of the cards in a deck contain the supplied
//           phrase.
//
//           Hints:
//           - string1.contains(string2) will be quite useful
//             here :)
//           - Again, the name of this function hints at a useful
//             list function we learned!
//

// check if any card in deck contains phrase
fun anyContainsPhrase(deck: Deck, phrase: String): Boolean {
    for (index in 0..(deck.cardList.count() - 1)) {
        if (deck.cardList[index].front.contains(phrase)) {
            return true
        }
        if (deck.cardList[index].back.contains(phrase)) {
            return true
        }
    }
    return false
}

// tests areAllOneWordAnswers
@EnabledTest
fun testAreAllOneWordAnswers() {
    testSame((::areAllOneWordAnswers)(deck1), true, "no spaces")
    testSame((::areAllOneWordAnswers)(deck2), false, "spaces")
}

// tests anyContainsPhrase
@EnabledTest
fun testAnyContainsPhrase() {
    testSame(anyContainsPhrase(deck1, "2+2"), true, "2+2 in deck1")
    testSame(anyContainsPhrase(deck2, "0"), false, "0 not in deck2")
}

fun main() {
    runEnabledTests(this)
}

main()
