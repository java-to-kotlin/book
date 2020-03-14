
import book.processFiles

tasks {
    create("build") {
        doFirst {
            processFiles(project.projectDir, project.rootProject.projectDir.toPath().resolve("code/src").toFile())
        }
    }
}
