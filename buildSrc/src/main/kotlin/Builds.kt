import java.util.*

object Builds {
    const val MIN_VERSION = 21
    const val COMPILE_VERSION = 30
    const val TARGET_VERSION = 30
    const val BUILD_TOOLS = "30.0.2"
    const val APP_ID = "ru.nightgoat.needdrummer"

    const val APK_NAME = "NeedDrummer-"

    const val MINOR_VERSION = "MINOR_VERSION"
    const val MAJOR_VERSION = "MAJOR_VERSION"
    const val PREFIX_VERSION = "PREFIX_VERSION"

    val codeDirs = arrayListOf(
        "src/main/kotlin"
    )

    val resDirs = arrayListOf(
        "src/main/res",
        "src/main/res/fragments",
        "src/main/res/pages",
        "src/main/res/items",
        "src/main/res/icons"
    )

    object App {
        const val VERSION_CODE = 1
        const val VERSION_NAME = "0.0.1"
    }

    object Types {
        const val DEBUG = "debug"
        const val RELEASE = "release"
    }

    const val DEVELOP_BRANCH_NAME = "develop"

    private const val ASSEMBLE = "assemble"
    private val incrementalTaskNames by lazy {
        listOf(
            "${ASSEMBLE}${Types.DEBUG.capitalize(Locale.getDefault())}",
            "${ASSEMBLE}${Types.RELEASE.capitalize(Locale.getDefault())}"
        )
    }

    fun isTaskNameValidForWriteVersionInFile(taskName: String): Boolean {
        return incrementalTaskNames.any { it == taskName }
    }
}