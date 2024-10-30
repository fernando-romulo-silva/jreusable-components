package org.reusablecomponents.messaging;

public class MessagingConst {

  private MessagingConst() {
    throw new UnsupportedOperationException("You can't instanciate this class");
  }

  public static final String JSON_LAYOUT = """
      {
         "id": "${id}",
         "what": {
      	"dataIn" : "${dataIn}",
      	"dataOut" : "${dataOut}"
         },
         "when": {
      	"dateTime" : "${dateTime}",
      	"zoneId" : "${zoneId}"
         },
         "where": {
      	"application" : "${application}",
      	"machine" : "${machine}"
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
          <what>
              <dataIn>${dataIn}</dataIn>
              <dataOut>${dataOut}</dataOut>
          </what>
          <when>
              <dateTime>${dateTime}</dateTime>
              <zoneId>${zoneId}</zoneId>
          </when>
          <where>
              <application>${application}</application>
              <machine>${machine}</machine>
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
      what:
        dataIn: "${dataIn}"
        dataOut: "${dataOut}"
      when:
        dateTime: "${dateTime}"
        zoneId: "${zoneId}"
      where:
        application: "${application}"
        machine: "${machine}"
      who:
        login: "${login}"
        session: "${session}"
        realm: "${realm}"
      why:
        reason: "${reason}"
        description: "${description}"
      """;
}
