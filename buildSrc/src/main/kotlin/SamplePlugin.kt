import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class SampleTask : DefaultTask() {
    init {
        description = "Just a sample template task"
        group = "custom"
    }

    @get:Input
    @get:Option(description = "Whom to greet?")
    abstract val username: Property<String>
    // property `name` is reserved ;^)

    @TaskAction
    fun greet() {
        println("Hello, ${username.getOrElse("Unknown")}!")
    }
}

class SamplePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.register("SamplePluginSampleTask", SampleTask::class.java) {
            this.username.set("world!")
        }
    }
}