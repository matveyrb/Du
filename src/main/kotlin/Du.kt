package du

import java.io.File
import java.util.*

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
        var text:String
        var unit = h

        for (i in iterateList) {
            text = i.bufferedReader().readText()
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
                    listForSum.add(text.length / kb)
                    resultList.add("Name: $i = ${(text.length / kb)} KB")
                }
                "MB" -> {
                    listForSum.add(text.length / mb)
                    resultList.add("Name: $i = ${(text.length / mb)} MB")
                }
                "GB" -> {
                    listForSum.add(text.length / gb)
                    resultList.add("Name: $i = ${(text.length / gb)} GB")
                }
                else -> {
                    unit = "B"
                    listForSum.add(text.length)
                    resultList.add("Name: $i = ${text.length} B")
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