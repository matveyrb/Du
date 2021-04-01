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


class Du(private val h: Boolean, private val c: Boolean, private val si: Boolean,
         private val iFile: MutableList<File>) {



    fun reader():Int {
        val iterateList = mutableListOf<File>()
        val preIterateList = iFile
        for (i in preIterateList) {
            if ('.' !in i.toString()) {
                val addList = listFilesWithSubFolders(iFile[0])!!.toMutableList()
                for (j in addList) {
                    iterateList.add(j)
                }
            } else {
                iterateList.add(i)
            }
        }
        val listForTest = mutableListOf<Int>()
        val resultList = mutableListOf<String>()
        val listForSum = mutableListOf<Double>()
        var text:Double
        var kb = 1024
        var mb = 1024 * 1024
        var gb = 1024 * 1024 * 1024

        for (i in iterateList) {
            text = size(Paths.get(i.toString())).toDouble()
            if (si) {
                kb = 1000
                mb = 1000 * 1000
                gb = 1000 * 1000 * 1000
            }
            if (h) {
                when {
                    text >= gb -> {
                        listForSum.add((text))
                        resultList.add("Name: $i = ${(text / gb)} GB")
                    }
                    text >= mb -> {
                        listForSum.add(text)
                        resultList.add("Name: $i = ${(text / mb)} MB")
                    }
                    text >= kb -> {
                        listForSum.add(text)
                        resultList.add("Name: $i = ${(text / kb)} KB")
                    }
                    text < kb -> {
                            listForSum.add(text)
                            resultList.add("Name: $i = ${(text)} B")
                    }
                }
            } else {
                listForSum.add(text)
                resultList.add("Name: $i = $text B")
            }
        }
        if (c) {
            when (c) {
                listForSum.sum() >= gb -> {
                    println("Sum of file sizes = ${listForSum.sum() / gb} GB")
                    return 0
                }
                listForSum.sum() >= mb -> {
                    println("Sum of file sizes = ${listForSum.sum() / mb} MB")
                    return 0
                }
                listForSum.sum() >= kb -> {
                    println("Sum of file sizes = ${listForSum.sum() / kb} KB")
                    return 0
                }
                listForSum.sum() < kb -> {
                    println("Sum of file sizes = ${listForSum.sum()} B")
                }
            }
        } else {
            for (i in resultList) {
                println(i)
            }
            return 0
        }
        return 1
    }
}