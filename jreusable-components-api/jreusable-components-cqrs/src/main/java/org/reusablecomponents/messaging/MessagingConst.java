package org.reusablecomponents.messaging;

/**
 * Constants for messaging layouts.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
public class MessagingConst {

  /**
   * Private constructor to prevent instantiation
   */
  private MessagingConst() {
    throw new UnsupportedOperationException("You can't instanciate this class");
  }

  /**
   * JSON layout for messaging.
   */
  public static final String JSON_LAYOUT = """
      {
         "id": "${id}",
         "origin": "${origin}",
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
            "application" : "${application}",
            "build" : "${build}",
            "version" : "${version}",
            "descriptor" : "${descriptor}"
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

  /**
   * XML layout for messaging.
   */
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
              <application>${application}</application>
              <version>${version}</version>
              <build>${build}</build>
              <descriptor>${descriptor}</descriptor>
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

  /**
   * YAML layout for messaging.
   */
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
        version: "${version}"
        build: "${build}"
        descriptor: "${descriptor}"
      who:
        login: "${login}"
        session: "${session}"
        realm: "${realm}"
      why:
        reason: "${reason}"
        description: "${description}"
      """;
}
