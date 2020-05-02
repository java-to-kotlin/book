import book.processFiles
import book.runCommand

tasks {
    create("build") {
        doFirst {
            processFiles(
                textRoot = project.projectDir,
                workedExampleSrcRoot = project.rootProject.projectDir.resolve("../refactoring-to-kotlin-code"),
                digressionSrcRoot = project.rootProject.projectDir.resolve("code"),
                abortOnFailure = true
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

