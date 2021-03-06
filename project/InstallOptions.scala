import sbt._

import scala.xml.XML

case class JacocoAgent(jar: File, outFile: File) {
  def opts = s"-javaagent:${jar.absolutePath}=destfile=${outFile.absolutePath}"
}

case class InstallOptions(baseInstall: File, installDir: File, jvmHome: File, url: String, hostname: String,
                          port: Int, jacoco: Option[JacocoAgent]) {
  def writeXML(xmlFile: File) = {
    val optXml = <commands>
      <installer>
        <product>
          <name>EQUELLA</name>
          <version>
            &lt;
            No Product Version
            &gt;
          </version>
          <company>Apereo</company>
        </product>
        <local>{baseInstall.absolutePath}</local>
        <platform>linux64</platform>
      </installer>
      <install.path>{installDir.absolutePath}</install.path>
      <java>
        <jdk>{jvmHome.absolutePath}</jdk>
      </java>
      <datasource>
        <dbtype>postgresql</dbtype>
        <host>localhost</host>
        <port>5432</port>
        <database>equellatests</database>
        <username>equellatests</username>
        <password>password</password>
        <idtype>:</idtype>
      </datasource>
      <webserver>
        <url>{url}</url>
        <binding>all</binding>
        <host>{hostname}</host>
        <port>{port}</port>
        <context>/</context>
        <javaopts>-Dequella.dev=true -Dequella.autotest=true {jacoco.map(_.opts).getOrElse("")}</javaopts>
      </webserver>
      <service>
        <port>3000</port>
      </service>
      <proxy>
        <connection>false</connection>
      </proxy>
      <memory>
        <min>96m</min>
        <max>512m</max>
      </memory>
      <imagemagick>
        <path>/usr/bin</path>
      </imagemagick>
      <libav>
        <path>/usr/bin</path>
      </libav>
      <hashed.admin.password>SHA256:2a0fd3498c35eced0663c523a80125cbfd3fb8d1634ce87c2ccc020b924ac2d9</hashed.admin.password>
      <datastore>
        <initialise>true</initialise>
      </datastore>
      <tomcat>
        <path>{(installDir / "tomcat").absolutePath}</path>
      </tomcat>
      <admin.password>admin</admin.password>
    </commands>
    XML.save(xmlFile.absolutePath, optXml)
  }
}
