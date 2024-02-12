import khoury.EnabledTest
import khoury.runEnabledTests
import khoury.testSame
// -----------------------------------------------------------------
// Homework 5, Problem 3
// -----------------------------------------------------------------

// In this problem you'll practice designing a data class that has
// methods, making your first upgrade to the project.

// TODO 1/1: Design the data type TaggedFlashCard to represent a
//           single flash card.
//
//           You should be able to represent the text prompt on
//           the front of the card, the text answer on the back,
//           as well as any number of textual tags (such as "hard"
//           or "science" -- this shouldn't come from any fixed
//           set of options, but truly open to however someone
//           wishes to categorize their cards).
//
//           Each card should have two convenience methods:
//           - isTagged, which determines if the card has a
//             supplied tag (e.g., has this card been tagged
//             as "hard"?)
//           - fileFormat, which produces a textual representation
//             of the card as "front|back|tag1,tag2,..."; that is
//             all three parts of the card separated with the pipe
//             ('|') character, and further separate any tags with
//             a comma (',')
//
//           Include *at least* 3 example cards, and make sure to
//           test the methods (in a single testTaggedFlashCard
//           function that has been annotated as @EnabledTest).
//

// flash card that has a front/back as well as tags that describe attributes
data class TaggedFlashCard(val front: String, val back: String, val tags: List<String>) {
    // checks for if the specific tag is in the list of tags
    fun isTagged(tag: String): Boolean {
        return tag in tags
    }

    // formats the flash card into a string that follows the specified format
    fun fileFormat(): String {
        return front + sepCard + back + sepCard + tags.joinToString(sepTag)
    }
}

val card1 = TaggedFlashCard("2+2", "4", listOf("math", "addition", "plus"))
val card2 = TaggedFlashCard("How many letters in the alphabet", "26", listOf("english", "letters", "count"))
val card3 = TaggedFlashCard("What is the speed of light", "300000000m/s", listOf("science", "space", "speed"))

@EnabledTest
fun testTaggedFlashCard() {
    testSame(card1.isTagged("math"), true, "card 1 math tag")
    testSame(card1.isTagged("english"), false, "card 1 english tag")
    testSame(card2.isTagged("math"), false, "card 2 math tag")
    testSame(card2.isTagged("english"), true, "card 2 english tag")

    testSame(card1.fileFormat(), "2+2|4|math,addition,plus", "card 1 format")
    testSame(card2.fileFormat(), "How many letters in the alphabet|26|english,letters,count", "card 2 format")
}

val sepCard = "|"
val sepTag = ","
// (just useful values for
// the separation characters)

fun main() {
    runEnabledTests(this)
}

main()
