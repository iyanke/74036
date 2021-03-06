import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2021.2"

project {

    vcsRoot(HttpsGithubComIyankeBigdata)

    buildType(TestBuilder1)
    buildType(TestBuilder2)
    buildType(TestBuilder3)
}

object TestBuilder1 : BuildType({
    name = "Test Builder1"

    vcs {
        root(HttpsGithubComIyankeBigdata)
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            scriptContent = "echo build1"
        }
    }

    triggers {
        vcs {
            branchFilter = ""
        }
    }

    dependencies {
        snapshot(TestBuilder3) {        }
        snapshot(TestBuilder2) {        }
    }
})

object TestBuilder3 : BuildType({
    name = "Test Builder3"

    vcs {
        root(HttpsGithubComIyankeBigdata)
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            scriptContent = "echo build3"
        }
    }
})

object TestBuilder2 : BuildType({
    name = "Test Builder2"

    vcs {
        root(HttpsGithubComIyankeBigdata)
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            scriptContent = "echo build2"
        }
    }
})

object HttpsGithubComIyankeBigdata : GitVcsRoot({
    name = "https://github.com/iyanke/bigdata"
    url = "https://github.com/iyanke/bigdata"
    branch = "refs/heads/master"
})
