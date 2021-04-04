package du

import org.apache.commons.io.FileUtils.sizeOf
import org.apache.commons.io.FileUtils.sizeOfDirectory
import java.io.File


class Du(private val h: Boolean, private val c: Boolean, private val si: Boolean,
         private val iFile: MutableList<File>) {



    fun reader():List<Double> {
        val iterateList = iFile.toSet()


        val listForTest = mutableListOf<Double>()
        val resultList = mutableListOf<String>()
        val listForSum = mutableListOf<Double>()
        var sizeDouble:Double
        var kb = 1024
        var mb = 1024 * 1024
        var gb = 1024 * 1024 * 1024

        for (i in iterateList) {
           val size = if (i.isDirectory){
                sizeOfDirectory(i)
            } else sizeOf(i)
            sizeDouble = size.toDouble()
            if (si) {
                kb = 1000
                mb = 1000 * 1000
                gb = 1000 * 1000 * 1000
            }
            if (h) {
                when {
                    sizeDouble >= gb -> {
                        listForSum.add(sizeDouble)
                        listForTest.add(sizeDouble / gb)
                        resultList.add("Name: $i = ${(sizeDouble / gb)} GB")
                    }
                    sizeDouble >= mb -> {
                        listForSum.add(sizeDouble)
                        listForTest.add(sizeDouble / mb)
                        resultList.add("Name: $i = ${(sizeDouble / mb)} MB")
                    }
                    sizeDouble >= kb -> {
                        listForSum.add(sizeDouble)
                        listForTest.add(sizeDouble / kb)
                        resultList.add("Name: $i = ${(sizeDouble / kb)} KB")
                    }
                    sizeDouble < kb -> {
                            listForSum.add(sizeDouble)
                            listForTest.add(sizeDouble)
                            resultList.add("Name: $i = ${(sizeDouble)} B")
                    }
                }
            } else {
                listForSum.add(sizeDouble)
                listForTest.add(sizeDouble)
                resultList.add("Name: $i = $sizeDouble B")
            }
        }
        if (c) {
            if (h) {
                when (c) {
                    listForSum.sum() >= gb -> {
                        println("Sum of file sizes = ${listForSum.sum() / gb} GB")
                        return listOf(listForSum.sum() / gb)
                    }
                    listForSum.sum() >= mb -> {
                        println("Sum of file sizes = ${listForSum.sum() / mb} MB")
                        return listOf(listForSum.sum() / mb)
                    }
                    listForSum.sum() >= kb -> {
                        println("Sum of file sizes = ${listForSum.sum() / kb} KB")
                        return listOf(listForSum.sum() / kb)
                    }
                    listForSum.sum() < kb -> {
                        println("Sum of file sizes = ${listForSum.sum()} B")
                        return listOf(listForSum.sum())
                    }
                }
            } else {
                println("Sum of file sizes = ${listForSum.sum()} B")
                return listOf(listForSum.sum())
            }
        } else {
            for (i in resultList) {
                println(i)
            }
            return listForTest
        }
        return listForTest
    }
}