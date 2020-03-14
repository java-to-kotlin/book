
import book.processFiles

tasks {
    create("build") {
        doFirst {
            processFiles(project.projectDir, File("../code/src"))
        }
    }
}
