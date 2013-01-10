import sbt._
import Keys._
object ScalaMilestonePlugin extends Plugin {

  // in the future we'll stop hardcoding scala version into plugin's source but for now it's ok
  private val scalaMilestone = "2.11.0-M1"

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
    val magnetText = "\033[35m"
    val redText = "\033[31m"
    val defaultText = "\033[39m"
    val reset = "\033[0m"
    val boldOn = "\033[1m"
    val boldOff = "\033[22m"
    val scalaVersionFormated = {
      val wrongVersionMsg = "("+ redText + "WRONG, probably you forgot to add " + boldOn + "scalaMilestonePluginSettings" + boldOff + " to your project's settings" + defaultText + ")"
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
         | :','  ~~~~    '. '~        %1$sThe release-against-scala-milestone v0.1
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

  // Cosmetics, info, etc. below
  // some constatns are duplicated below because sbt 0.12.x doesn't allow to declare vals in .sbt files

  val infoCommand = Command.command("release-against-scala-milestone-info") { state =>
    val msg =
      """|
         |Your build settings has been altered by `release-against-scala-2.11-M1.sbt` file.
         |Once you are done with releasing against Scala milestone simply remove that file.
         |
         |The following settings has been altered:
         |  crossScalaVersions, scalaBinaryVersion, resolvers
         |
         |In the future we'll fix sbt so it handles milestones correctly out of the box.
         |The file has been brought to you by Typesafe. If it doesn't work, feel free to ping
         |us at scala-internals mailing list.
         |
         |KNOWN ISSUES
         |
         |  1. Prints a warning about mismatching binary versions for Scala dependencies like
         |     library, actors or compiler. This is a bug in sbt 0.12.x that you can safely
         |     ignore.
         |""".stripMargin
    state.log.info(msg)
    state
  }
}
