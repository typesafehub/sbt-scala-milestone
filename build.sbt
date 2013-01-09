sbtPlugin := true

name := "scala-milestone-plugin"

organization := "com.typesafe"

version := "0.1"

publishMavenStyle := false

credentials += Credentials(Path.userHome / ".ivy2" / ".sbtcredentials")

publishTo <<= (version) { version =>
   val u = "http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"
   Some(Resolver.url("sbt-plugin-releases", url(u))(Resolver.ivyStylePatterns))
}
