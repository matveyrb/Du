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
        assertEquals(listOf(18.0), DuStart().start(arrayOf("-c", "words.txt")))
    }

    @Test
    fun taskTest1() {
        val file = File("words.txt")
        file.bufferedWriter().use {
            for (i in 0..9999)
                it.write("There is some text")
        }
        assertEquals(listOf(180000.0), DuStart().start(arrayOf("-c","words.txt")))
    }

    @Test
    fun taskTest2() {
        val file = File("words.txt")
        file.bufferedWriter().use {
            for (i in 0..3)
                it.write("There is some text")
        }
        assertEquals(listOf(72.0), DuStart().start(arrayOf("words.txt")))
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
        assertEquals(listOf(360.0), DuStart().start(arrayOf("-h","-c","--si","words.txt","moreWords.txt")))
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
        assertEquals(listOf(180.0,180.0), DuStart().start(arrayOf("-h","--si","words.txt","moreWords.txt")))
    }
}
