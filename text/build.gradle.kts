
import book.processFiles

tasks {
    create("build") {
        doFirst {
            processFiles(project.projectDir, project.rootProject.projectDir.toPath().resolve("../refactoring-to-kotlin-code").toFile())
        }
    }
}
