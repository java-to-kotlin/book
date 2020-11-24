import book.processFiles
import book.runCommand

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

// This causes us not to find the tags when "build" is run, even though it has completed
//    create("clean") {
//        doFirst {
//            "./retag-worked-example".runCommand(workingDir = project.rootProject.projectDir)
//            Thread.sleep(2000)
//        }
//    }
}

