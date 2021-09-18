rootProject.buildFileName = "build.gradle.kts"
rootProject.name = "NeedDrummer"
include(":app")
buildCache {
    local {
        directory = File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 2
    }
}