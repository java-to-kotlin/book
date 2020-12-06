package book

import com.natpryce.Result
import com.natpryce.Success
import com.natpryce.map
import com.natpryce.resultFrom
import java.io.File

interface CodeFile {
    val sourceRoot: File
    val relativePath: String

    val file get() = sourceRoot.resolve(relativePath)
    val exists get() = lines is Success
    val extension: String get() = file.extension
    val lines: Result<List<String>, Exception>
}

data class DiskFile(
    override val sourceRoot: File,
    override val relativePath: String
) : CodeFile {

    override fun toString() = file.canonicalPath

    override val lines: Result<List<String>, Exception> by lazy {
        resultFrom { file.readLines() }
    }
}

data class GitFile(
    override val sourceRoot: File,
    override val relativePath: String,
    val version: String
) : CodeFile {

    override val lines by lazy {
        "git show $version:${relativePath}"
            .runCommand(workingDir = sourceRoot)
            .map { it.lines() }
    }

    override fun toString() = "$version: ${file.canonicalPath}"
}