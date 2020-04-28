import book.processFiles

tasks {
    create("build") {
        doFirst {
            processFiles(
                textRoot =
                    project.projectDir,
                workedExampleSrcRoot =
                    project.rootProject.projectDir.resolve("../refactoring-to-kotlin-code").canonicalFile,
                digressionSrcRoot =
                    project.rootProject.projectDir.resolve("code").canonicalFile,
                abortOnFailure = true
            )
        }
    }
}
