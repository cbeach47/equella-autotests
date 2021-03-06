import com.typesafe.config.Config
import sbt.{AutoPlugin, File, settingKey, taskKey}

object TestKeys extends AutoPlugin {

  object autoImport {
    lazy val installDir = settingKey[File]("Dir to install EQUELLA into")
    lazy val installEquella = taskKey[File]("Install EQUELLA locally")
    lazy val startEquella = taskKey[Unit]("Start the EQUELLA service")
    lazy val stopEquella = taskKey[Unit]("Stop the EQUELLA service")
    lazy val installOptions = taskKey[InstallOptions]("EQUELLA installer options")
    lazy val installerZip = settingKey[Option[File]]("The installer zip")
    lazy val buildConfig = settingKey[Config]("The build config options")
    lazy val setupForTests = taskKey[Unit]("Setup the EQUELLA server for tests")
    lazy val coverageReport = taskKey[Unit]("Generate coverage report")
  }
}
