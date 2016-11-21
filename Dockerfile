FROM openjdk:jre

ADD target/xebia-essentials-slackbot-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/xebia-essentials-slackbot.jar

ENTRYPOINT ["java", "-jar", "/opt/xebia-essentials-slackbot.jar"]
