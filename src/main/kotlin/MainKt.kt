import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import java.io.File

class Lt(private val configFile: File) : NoOpCliktCommand(help = "lt") {
    override fun aliases(): Map<String, List<String>> {
        return configFile.readLines().map { it.split("=", limit = 2) }
            .associate { it[0].trim() to it[1].trim().split(Regex("\\s+")) }
    }
}

class Md : CliktCommand(help = "Import market data") {

    val cfg: File by argument("cfg", help="File with list of instruments to retrieve market data for, and their config.").file()

    override fun run() {
        var instruments = cfg.readLines().map { it.split(";", limit = 3) }.map { r -> r.map { it.trim() } }
        println(instruments)
    }
}

fun main(args: Array<String>) {
    // The file path is relative to the project root for use with `runsample`
    Lt(File("/Users/said/.ledgertools/cfg"))
        .subcommands(Md())
        .main(args)
}

