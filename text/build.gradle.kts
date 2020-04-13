
import book.processFiles

tasks {
    create("build") {
        doFirst {
            processFiles(
                dir = project.projectDir,
                workedExampleSrcRoot = project.rootProject.projectDir.resolve("../refactoring-to-kotlin-code"),
                digressionSrcRoot = project.rootProject.projectDir.resolve("code")
            )
        }
    }
}
