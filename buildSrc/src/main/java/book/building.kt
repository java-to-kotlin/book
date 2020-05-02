package book

import com.natpryce.Result
import java.io.File
import java.util.concurrent.TimeUnit


fun String.runCommand(
    workingDir: File = File("."),
    timeoutAmount: Long = 2,
    timeoutUnit: TimeUnit = TimeUnit.SECONDS
): Result<String, Exception> = resultOf {
    val completedProcess = ProcessBuilder(split("\\s".toRegex()))
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start().apply { waitFor(timeoutAmount, timeoutUnit) }
    if (completedProcess.exitValue() == 0)
        completedProcess.inputStream.bufferedReader().readText()
    else
        error(completedProcess.errorStream.bufferedReader().readText() +
            "running $this in ${workingDir.canonicalPath}")
}

fun <T> T.printed(): T = this.also { println(it) }