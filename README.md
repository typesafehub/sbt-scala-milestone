# Sbt plugin for releasing against Scala milestones

This plugin configures your build so the right settings are used
for publishing your artifacs against Scala milestones of a major release of Scala
which are not binary compatible against each other thus has to be treated
specially.

## Installation

If you are using single project build configured with `build.sbt` file do:

    mkdir -p project && \
      (printf '\n' &&
       printf '//remove once you are done with releasing against Scala Milestone\n' &&
       printf 'addSbtPlugin("com.typesafe" %% "scala-milestone-plugin" %% "0.1")\n') >> project/local-plugins.sbt
    (printf '\n' &&
     printf '//remove once you are done with releasing against Scala Milestone\n' &&
     printf 'scalaMilestonePluginSettings\n') >> build.sbt


This will reconfigure your build to work properly with Scala 2.10.0-M1.

Optionally, add `project/local-plugins.sbt` to your `.gitignore` file so you won't add this plugin permamently (by commiting a change) by accident.
