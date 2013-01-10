# Sbt plugin for releasing against Scala milestones

This plugin configures your build so the right settings are used
for publishing your artifacs against Scala milestones of a major release of Scala
which are not binary compatible against each other thus has to be treated
specially.

## Installation

If you are using single project build configured with `build.sbt` file do:

    mkdir -p project && {
    cat <<EOM

    //remove once you are done with releasing against Scala Milestone
    addSbtPlugin("com.typesafe" % "scala-milestone-plugin" % "0.1")
    EOM
    } >> project/local-plugins.sbt

    { cat <<EOM

    //remove once you are done with releasing against Scala Milestone
    scalaMilestonePluginSettings

    EOM
    } >> build.sbt

This will add the `scala-milestone-plugin` to your project and append settings at the end of your `build.sbt` file that reconfigure your build to work properly with Scala 2.10.0-M1.

Optionally, add `project/local-plugins.sbt` to your `.gitignore` file so you won't add this plugin permamently (by commiting a change) by accident.
