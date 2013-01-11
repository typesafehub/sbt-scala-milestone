# Sbt plugin for releasing against Scala milestones

This plugin configures your build so the right settings are used
for publishing your artifacs against Scala milestones of a major release of Scala
which are not binary compatible against each other thus has to be treated
specially.

## Installation

### 1. Add the plugin

If you have bash handy here's copy&paste snippet you need to execute in root directory of your sbt project:
```
mkdir -p project && {
cat <<EOM

//remove once you are done with releasing against Scala Milestone
addSbtPlugin("com.typesafe.sbt" % "sbt-scala-milestone-plugin" % "1.0")
EOM
} >> project/local-plugins.sbt
```

If you are don't bash on your machine create file `project/local-plugins.sbt` with the following two lines:

```
//remove once you are done with releasing against Scala Milestone
addSbtPlugin("com.typesafe.sbt" % "sbt-scala-milestone-plugin" % "1.0")
```

### 2. Configure settings

Now you need to append `sbtScalaMilestonePluginSettings` to your settings.

If you are using single project build configured with `build.sbt` file here's bash snippet that will do the job for you:

```
{ cat <<EOM

//remove once you are done with releasing against Scala Milestone
sbtScalaMilestonePluginSettings

EOM
} >> build.sbt
```

Or you can do it manually by opening `build.sbt` and appending the following lines _at the end of the file_:

```
//remove once you are done with releasing against Scala Milestone
sbtScalaMilestonePluginSettings
```

Appending settings provided by `sbt-scala-milestone-plugin` at the end of your `build.sbt` file will reconfigure your build to work properly with Scala 2.11.0-M1.

Optionally, add `project/local-plugins.sbt` to your `.gitignore` file so you won't add this plugin permamently (by commiting a change) by accident.

## Usage

Once plugin is installed you can use regular commands and tasks like `publish-local` and `publish`. Once you are done with publishing against Scala milestone
just remove the plugin and reference to settings it provides.
