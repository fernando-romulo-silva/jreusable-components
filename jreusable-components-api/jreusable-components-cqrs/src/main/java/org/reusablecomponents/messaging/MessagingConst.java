package org.reusablecomponents.messaging;

public class MessagingConst {

  private MessagingConst() {
    throw new UnsupportedOperationException("You can't instanciate this class");
  }

  public static final String JSON_LAYOUT = """
      {
         "id": "${id}",
         "origin: "${origin},
         "status" : "${status}",
         "what": {
            "dataIn" : "${dataIn}",
            "dataOut" : "${dataOut}"
          },
         "when": {
            "dateTime" : "${dateTime}",
            "zoneId" : "${zoneId}"
         },
         "where": {
            "machine" : "${machine}",
            "build" : "{$build},
            "application" : "${application}",
            "version" : "${version}
         },
         "who": {
            "login" : "${login}",
            "session" : "${session}",
            "realm" : "${realm}"
         },
         "why": {
            "reason" : "${reason}",
            "description" : "${description}"
         }
      }""";

  public static final String XML_LAYOUT = """
      <?xml version="1.0" encoding="UTF-8" ?>
      <event>
          <id>${id}</id>
          <origin>${origin}</origin>
          <status>${status}</status>
          <what>
              <dataIn>${dataIn}</dataIn>
              <dataOut>${dataOut}</dataOut>
          </what>
          <when>
              <dateTime>${dateTime}</dateTime>
              <zoneId>${zoneId}</zoneId>
          </when>
          <where>
              <machine>${machine}</machine>
              <build>${build}</build>
              <application>${application}</application>
              <version>${version}</version>
          </where>
          <who>
              <login>${login}</login>
              <session>${session}</session>
              <realm>${realm}</realm>
          </who>
          <why>
              <reason>${reason}</reason>
              <description>${description}</description>
          </why>
      </event>""";

  public static final String YAML_LAYOUT = """
      ---
      id: "${id}"
      origin: "${origin}"
      status: "${status}"
      what:
        dataIn: "${dataIn}"
        dataOut: "${dataOut}"
      when:
        dateTime: "${dateTime}"
        zoneId: "${zoneId}"
      where:
        machine: "${machine}"
        application: "${application}"
        build: "${build}"
        version: "${version}
      who:
        login: "${login}"
        session: "${session}"
        realm: "${realm}"
      why:
        reason: "${reason}"
        description: "${description}"
      """;
}
