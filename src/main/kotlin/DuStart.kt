package du

import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.File
import java.io.IOException

class DuStart {

    @Option(name = "-h", required = false)
    private var h: String = ""

    @Option(name = "-c", required = false)
    private var c: Boolean = false

    @Option(name = "--si", required = false)
    private var si: Boolean = false

    @Argument(metaVar = "InputFile", required = false, usage = "InputFile", index = 0)
    private var iFile = mutableListOf<File>()


    fun start(args : Array<String>){
        val parser = CmdLineParser(this)
        try {
            parser.parseArgument(*args)
        } catch (e: CmdLineException) {
            println(e.message)
            println("du [-h|-c] [--si] file...")
            parser.printUsage(System.out)
            return
        }
        try {
            Du(h, c, si, iFile).reader()
        } catch (e: IOException) {
            println(e.message)
            return
        }
    }
}
fun main(args : Array<String>){
    DuStart().start(args)
}