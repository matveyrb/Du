package du
import org.junit.Assert.*
import org.junit.Test
import  java.io.File
class DuTest {
    @Test
    fun taskTest() {
        val file = File("words.txt")
        file.bufferedWriter().use {
            it.write("There is some text")
        }
        assertEquals(18, DuStart().start(arrayOf("-c", "words.txt")))
    }

    @Test
    fun taskTest1() {
        val file = File("words.txt")
        file.bufferedWriter().use {
            for (i in 0..9999)
                it.write("There is some text")
        }
        assertEquals(180000, DuStart().start(arrayOf("-c", "words.txt")))
    }

    @Test
    fun taskTest2() {
        val file = File("words.txt")
        file.bufferedWriter().use {
            for (i in 0..3)
                it.write("There is some ἱερο")
        }
        assertEquals(listOf(72), DuStart().start(arrayOf("words.txt")))
    }

    @Test
    fun taskTest3() {
        val file = File("words.txt")
        file.bufferedWriter().use {
            for (i in 0..9999)
                it.write("There is some text")
        }
        val file2 = File("moreWords.txt")
        file2.bufferedWriter().use {
            for (i in 0..9999)
                it.write("There is some text")
            }
        assertEquals(360, DuStart().start(arrayOf("-h","KB","-c","--si","words.txt","moreWords.txt")))
        }
    @Test
    fun taskTest4() {
        val file = File("words.txt")
        file.bufferedWriter().use {
            for (i in 0..9999)
                it.write("There is some text")
        }
        val file2 = File("moreWords.txt")
        file2.bufferedWriter().use {
            for (i in 0..9999)
                it.write("There is some text")
        }
        assertEquals(listOf(180,180), DuStart().start(arrayOf("-h","KB","--si","words.txt","moreWords.txt")))
    }
}
