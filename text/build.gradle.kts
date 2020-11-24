import book.SourceRoots
import book.processFile
import book.processFiles
import java.lang.System.getProperty

tasks {
    val retagCode = register<Exec>("retagCode") {
        workingDir(rootDir)
        commandLine("./retag-worked-example")
        inputs.dir(rootDir.resolve("../refactoring-to-kotlin-code"))
        outputs.file(rootProject.buildDir.resolve("tagged"))
    }

    val sourceRoots = SourceRoots(
        rootDir.resolve("../refactoring-to-kotlin-code"),
        rootDir.resolve("code")
    )

    register("build") {
        dependsOn(retagCode)

        doLast {
            processFiles(
                inputRoot = projectDir,
                outputRoot = projectDir,
                sourceRoots = sourceRoots,
                abortOnFailure = true,
                kotlinVersion = rootDir.resolve(".kotlin-version").readText().trim()
            )
        }
    }

    // Use ./gradlew expand-one -Dsingle-file=$FilePath$
    register("expand-one") {
        dependsOn(retagCode)

        doLast {
            val file = getProperty("single-file")?.let {
                File(it).takeIf { it.isFile }
            } ?: error("File not specified or found in property single-file")
            processFile(
                src = file,
                dest = file,
                roots = sourceRoots,
                abortOnFailure = true,
                kotlinVersion = rootDir.resolve(".kotlin-version").readText().trim()
            )
        }
    }
}

