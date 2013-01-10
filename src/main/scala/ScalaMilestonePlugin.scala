import sbt._
import Keys._
object ScalaMilestonePlugin extends Plugin {

  // in the future we'll stop hardcoding scala version into plugin's source but for now it's ok
  private val scalaMilestone = "2.11.0-M1"

  private val consoleFormatEnabled = ConsoleLogger.formatEnabled

  val scalaMilestonePluginSettings = Seq(
    scalaVersion := scalaMilestone,
    // disable cross building completely for releasing against milestones
    crossScalaVersions := Seq.empty,
    // milestones need to use full version for scalaBinaryVersion
    scalaBinaryVersion <<= scalaVersion.identity,
    // Make sure we have the sonatype release resolver for both Scala + other staged artifacts that haven't hit Maven Central
    resolvers += "Sonatype Release" at "https://oss.sonatype.org/content/repositories/releases"
  )

  override lazy val settings = Seq(
    onLoadMessage <<= welcomeMessage,
    commands += infoCommand
  )

  val welcomeMessage = (scalaVersion, name, version) apply { (sv: String, name: String, version: String) =>
    val magnetText = if (consoleFormatEnabled) "\033[35m" else ""
    val redText = if (consoleFormatEnabled) "\033[31m" else ""
    val defaultText = if (consoleFormatEnabled) "\033[39m" else ""
    val reset = if (consoleFormatEnabled) "\033[0m" else ""
    val boldOn = if (consoleFormatEnabled) "\033[1m" else ""
    val boldOff = if (consoleFormatEnabled) "\033[22m" else ""
    val scalaVersionFormated = {
      val wrongVersionMsg = "("+ redText + "WRONG! Probably you forgot to add " + boldOn + "scalaMilestonePluginSettings" + boldOff + " to your project's settings" + defaultText + ")"
      sv + (if (sv != scalaMilestone) " " + wrongVersionMsg else "")
    }
    def colorize(s: String) = Predef.augmentString(s).lines.map(line => magnetText + line + defaultText).mkString("\n")
    // I wish I could use Scala's string interpolation below...
    colorize {
      """|
         |                    /
         |               ,.. / 
         |             ,'   '; 
         |  ,,.__    _,' /';  .
         | :','  ~~~~    '. '~        %1$sThe scala-milestone-plugin v1.0
         |:' (   )         ):,        %1$sis %2$senabled%3$s.
         |'. '. .=----=..-~ .;'
         | '  ;' ::    ':. '"  
         |   (:  ':     ;)     
         |    \\  '"   ./      
         |     '"      '"      
         |                (by DR J)
         |""".format(defaultText, boldOn, boldOff).stripMargin
    } +
      """|
         |
         |Your project %1$s-%2$s
         |is configured to be released against Scala %3$s.
         |
         |Type %4$srelease-against-scala-milestone-info%5$s for more information.
         |
         |""".format(name, version, scalaVersionFormated, boldOn, boldOff).stripMargin
  }

  val infoCommand = Command.command("release-against-scala-milestone-info") { state =>
    val msg =
      """|
         |Your build settings has been altered by scala-milestone-plugin.
         |Once you are done with releasing against Scala milestone simply remove that plugin.
         |
         |The following settings has been altered:
         |  crossScalaVersions, scalaBinaryVersion, scalaVersion, resolvers
         |
         |In the future we'll fix sbt so it handles milestones correctly out of the box.
         |This plugin has been brought to you by Typesafe. If it doesn't work, feel free to ping
         |us at scala-internals mailing list.
         |
         |KNOWN ISSUES
         |
         |  1. Sbt prints a warning about mismatching binary versions for Scala dependencies like
         |     library, actors or compiler. This is a bug in sbt 0.12.x that you can safely
         |     ignore.
         |""".stripMargin
    state.log.info(msg)
    state
  }
}
