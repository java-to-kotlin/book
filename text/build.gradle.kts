import book.SourceRoots
import book.processFile
import book.processFiles
import book.runCommand
import java.lang.System.getProperty

tasks {
    val retagCode = register<Exec>("retagCode") {
        workingDir(rootDir)
        commandLine("./retag-worked-example")
        inputs.dir(rootDir.resolve("../refactoring-to-kotlin-code"))
        outputs.file(rootProject.buildDir.resolve("tagged"))
    }

    register("build") {
        dependsOn(retagCode)

        doLast {
            processFiles(
                textRoot = projectDir,
                workedExampleSrcRoot = rootDir.resolve("../refactoring-to-kotlin-code"),
                digressionSrcRoot = rootDir.resolve("code"),
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
                roots = SourceRoots(
                    rootDir.resolve("../refactoring-to-kotlin-code"),
                    rootDir.resolve("code")
                ),
                abortOnFailure = true,
                kotlinVersion = rootDir.resolve(".kotlin-version").readText().trim()
            )
        }
    }

// This causes us not to find the tags when "build" is run, even though it has completed
//    create("clean") {
//        doFirst {
//            "./retag-worked-example".runCommand(workingDir = project.rootProject.projectDir)
//            Thread.sleep(2000)
//        }
//    }
}

