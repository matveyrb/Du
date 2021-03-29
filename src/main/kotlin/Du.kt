package du
import java.io.IOException
import java.nio.file.*

import java.nio.file.attribute.BasicFileAttributes

import java.util.concurrent.atomic.AtomicLong
import java.io.File
import java.util.*


fun size(path: Path?): Long {
    val size = AtomicLong(0)
    try {
        Files.walkFileTree(path!!, object : SimpleFileVisitor<Path>() {
            override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
                size.addAndGet(attrs.size())
                return FileVisitResult.CONTINUE
            }

            override fun visitFileFailed(file: Path, exc: IOException): FileVisitResult {
                println("skipped: $file ($exc)")
                return FileVisitResult.CONTINUE
            }

            override fun postVisitDirectory(dir: Path, exc: IOException): FileVisitResult {
                println("had trouble traversing: $dir ($exc)")
                return FileVisitResult.CONTINUE
            }
        })
    } catch (e: IOException) {
        throw AssertionError("walkFileTree will not throw IOException if the FileVisitor does not")
    }
    return size.get()
}

fun listFilesWithSubFolders(dir: File): ArrayList<File>? {
    val files = ArrayList<File>()
    for (file in dir.listFiles()!!) {
        if (file.isDirectory) files.addAll(listFilesWithSubFolders(file)!!) else files.add(file)
    }
    return files
}


class Du(private val h: String, private val c: Boolean, private val si: Boolean,
         private val iFile: MutableList<File>) {



    fun reader():Int {
        var iterateList = iFile
        if ('.' !in iFile[0].toString()) {
            iterateList = listFilesWithSubFolders(iFile[0])!!.toMutableList()
        }
        val listForTest = mutableListOf<Int>()
        val resultList = mutableListOf<String>()
        val listForSum = mutableListOf<Int>()
        var text:Int
        var unit = h

        for (i in iterateList) {
            text = size(Paths.get(i.toString())).toInt()
            var kb = 1024
            var mb = 1024 * 1024
            var gb = 1024 * 1024 * 1024
            if (si) {
                kb = 1000
                mb = 1000 * 1000
                gb = 1000 * 1000 * 1000
            }
            when (h) {
                "KB" -> {
                    listForSum.add(text / kb)
                    resultList.add("Name: $i = ${(text / kb)} KB")
                }
                "MB" -> {
                    listForSum.add(text / mb)
                    resultList.add("Name: $i = ${(text/ mb)} MB")
                }
                "GB" -> {
                    listForSum.add(text / gb)
                    resultList.add("Name: $i = ${(text/ gb)} GB")
                }
                else -> {
                    unit = "B"
                    listForSum.add(text)
                    resultList.add("Name: $i = ${text} B")
                }
            }
        }
        if (c) {
            println("Sum of file sizes = ${listForSum.sumBy { it }} $unit")
            return 0
        }
        for (i in resultList) {
            println(i)
        }
        return 0
    }
}