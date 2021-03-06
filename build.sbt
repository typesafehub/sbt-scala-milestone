sbtPlugin := true

name := "sbt-scala-milestone-plugin"

organization := "com.typesafe.sbt"

version := "1.0"

publishMavenStyle := false

credentials += Credentials(Path.userHome / ".ivy2" / ".sbtcredentials")

publishTo <<= (version) { version =>
   val u = "http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"
   Some(Resolver.url("sbt-plugin-releases", url(u))(Resolver.ivyStylePatterns))
}
