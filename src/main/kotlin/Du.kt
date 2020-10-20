package du

import java.io.File


class Du(private val h: String, private val c: Boolean, private val si: Boolean,
         private val iFile: MutableList<File>) {

    fun reader():Any {
        val listForTest = mutableListOf<Int>()
        val listForSum = mutableListOf<Int>()
        var text = ""
        for (i in iFile) {
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
                "KB" -> listForSum.add(text.length / kb)
                "MB" -> listForSum.add(text.length / mb)
                "GB" -> listForSum.add(text.length / gb)
                else -> listForSum.add(text.length)
            }
        }
        if (c) {
            println(listForSum.sumBy { it })
            return listForSum.sumBy { it }
        }
         for (i in listForSum) {
             println(i)
             listForTest.add(i)
         }
        return listForTest
    }
}