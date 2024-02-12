import khoury.EnabledTest
import khoury.fileExists
import khoury.fileReadAsList
import khoury.runEnabledTests
import khoury.testSame
// -----------------------------------------------------------------
// Homework 3, Problem 1
// -----------------------------------------------------------------

// Let's write a function that pulls together two related files
// of data and produces some useful summary data.
//
// Before coding, look at the two supplied example files:
//
// - names.txt:   one name (First Last) per line
//
// - records.txt: each line is a comma-separated list of either
//                W or L (win or lose); these correspond to the
//                student names in the first file (i.e., line 1
//                of names corresponds to line 1 of records...
//                until we don't have records for some students)
//                and represent magical battles fought, and how
//                each worked out. For example, Harry Potter has
//                had 7 magical battles, having won 5 of them.
//
// To make sure the students get adequate support, you are going
// to make a useful function to pull all of this into a single
// list of helpful info.
//

// TODO 1/1: Finish designing the function buildList that accepts
//           three parameters...
//           1. The desired size of the resulting list (you can
//              assume this will be non-negative).
//           2. A file path that contains first/last names,
//              one per line.
//           3. A file path that contains win/loss records,
//              where each line looks like "W,L,W,..." (some
//              number of comma-separated W/L's.
//
//           The function must produce a list of strings using
//           the list constructor:
//
//           val resultList = List<String>(
//               desiredSize,
//               ::initFunction
//           )
//
//           Here is how you should populate the resulting list
//           with the initFunction...
//
//           - For each entry, produce a string that combines the
//             first/last initials of the name in the first file,
//             with a summarization of their win/loss record
//             (either "is crushing it" if there are at least as
//             many W's as L's, or "needs support" otherwise).
//
//           - If for a particular entry, there is neither a name
//             nor a summarization record, produce an empty string.
//
//           - If there is a name, but there isn't a win/loss
//             record, simply indicate "is unknown" for the
//             record summarization.
//
//           - If there isn't a name, but there is a win/loss
//             record, simply indicate "PD" as the initials
//             (standing for "Person Doe").
//
//           To help, you have been supplied some tests. Here are
//           some hints...
//
//           - The string.split(delimeter) function will be useful
//             to produce a sequence from a string when there is a
//             known pattern of separation (like spaces in the
//             names file, and commas in the win/loss records).
//             Note that splitting an empty string will result in
//             a list with one empty string, which is often not
//             what you want :(
//
//           - Use sequence.size to determine how many elements are
//             in a sequence; you can similarly use the `in`
//             operator to determine if a particular index is in
//             sequence.indices
//
//           - To produce your initialization function, you likely
//             will need it to define it within buildList to make
//             sure that information about the names and win/loss
//             records are in scope.
//
//           - And lastly, since this problem has so many pieces,
//             here's a suggested method of attack...
//             1. Start by getting the contents of the two files
//                using the fileReadAsList function from the
//                Khoury library; println to make sure they match
//                what you see in the files!
//             2. Try using one of the list functions from this
//                week (cough! map) to convert names to initials,
//                and win records to summaries; you can use
//                printing and tests to make sure this is working.
//             3. Now you are in reasonable shape to start on the
//                constructor/initialization functions described
//                above :)
//

val nameUnknown = "PD"
val recordWin = "is crushing it"
val recordLoss = "needs support"
val recordUnknown = "is unknown"
var nameList: List<String> = listOf()
var recordList: List<String> = listOf()

// creates a list with initials and suggestions for instructors using the list constructor
fun buildList(length: Int, namePath: String, recordPath: String): List<String> {
    if (fileExists(namePath)) {
        nameList = fileReadAsList(namePath)
    }
    if (fileExists(recordPath)) {
        recordList = fileReadAsList(recordPath)
    }
    return List<String>(length, ::initFunction)
}

// creates a string with initials and suggestions for instructors using the current line number
fun initFunction(currentLine: Int): String {
    var currentName: String = ""
    var currentRecord: String = ""
    var initials: String = ""
    var recordStatement: String = ""
    if (nameList.isEmpty() && recordList.isEmpty()) {
        return ""
    }
    if (nameList.drop(currentLine).isEmpty()) {
        currentName = " "
    } else {
        currentName = nameList.drop(currentLine)[0]
    }
    if (recordList.drop(currentLine).isEmpty()) {
        currentRecord = " "
    } else {
        currentRecord = recordList.drop(currentLine)[0]
    }
    if (currentName == " ") {
        initials = nameUnknown
    } else {
        initials = currentName[0].toString() + currentName[(currentName.indexOf(" ") + 1)].toString()
    }
    if (currentRecord == " ") {
        recordStatement = recordUnknown
    } else {
        var winCount: Int = 0
        var lossCount: Int = 0
        for (currentChar in 0..(currentRecord.length - 1)) {
            if (currentRecord[currentChar].equals('W')) {
                winCount += 1
            }
            if (currentRecord[currentChar].equals('L')) {
                lossCount += 1
            }
        }
        if (lossCount == 0 && winCount == 0) {
            recordStatement = recordUnknown
        } else if (winCount >= lossCount) {
            recordStatement = recordWin
        } else if (lossCount > winCount) {
            recordStatement = recordLoss
        }
    }
    return "$initials $recordStatement"
}

fun main() {
    runEnabledTests(this)
}
main()

// tests buildlist
@EnabledTest
fun testBuildList() {
    testSame(
        buildList(2, "BADNAMES.TXT", "BADRECORDS.TXT"),
        listOf("", ""),
        "emptiness",
    )

    testSame(
        buildList(1, "BADNAMES.TXT", "records.txt"),
        listOf("PD is crushing it"),
        "PD",
    )

    testSame(
        buildList(3, "names.txt", "records.txt"),
        listOf(
            "HP is crushing it",
            "HG is crushing it",
            "RW is crushing it",
        ),
        "instructors",
    )

    testSame(
        buildList(7, "names.txt", "records.txt"),
        listOf(
            "HP is crushing it",
            "HG is crushing it",
            "RW is crushing it",
            "CC needs support",
            "NL is crushing it",
            "LL is unknown",
            "GW is unknown",
        ),
        "army",
    )
}
