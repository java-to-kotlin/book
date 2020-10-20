import book.processFiles
import book.runCommand

tasks {
    val retagCode = register<Exec>("retagCode") {
        workingDir(rootProject.rootDir)
        commandLine("./retag-worked-example")
    }

    register("build") {
        dependsOn(retagCode)

        doLast {
            processFiles(
                textRoot = project.projectDir,
                workedExampleSrcRoot = project.rootProject.projectDir.resolve("../refactoring-to-kotlin-code"),
                digressionSrcRoot = project.rootProject.projectDir.resolve("code"),
                abortOnFailure = true,
                kotlinVersion = project.projectDir.parentFile.resolve(".kotlin-version").readText().trim()
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

