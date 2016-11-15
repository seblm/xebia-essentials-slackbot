package xebiaessentialsslackbot;

import com.ullink.slack.simpleslackapi.SlackAttachment;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.IOException;

public class Bot {

    public static void main(String[] args) {
        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(args[0]);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                session.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        try {
            session.connect();
            session.addMessagePostedListener((event, session1) -> {
                if (event.getMessageContent().contains("card") && event.getMessageContent().contains("day")) {
                    // TODO call repository and transform Card to slack attachment
                    SlackAttachment slackAttachment = new SlackAttachment(
                            "Start with acceptance criteria\n\n",
                            "Start with acceptance criteria\n\n",
                            "<p>Acceptance criteria should be part of your definition of ready. Make sure\n" +
                                    "everyone has a common understanding of what they are before building anything.\n" +
                                    "Safeguard your acceptance criteria in automated tests.</p>\n",
                            "card of the day: acceptance-criteria");
                    slackAttachment.setColor("#6DC726");
                    slackAttachment.setTitleLink("http://essentials.xebia.com/acceptance-criteria");
                    session.sendMessage(event.getChannel(), "", slackAttachment);
                }
                System.out.println(event);
            });
            while (true) {
                Thread.sleep(Long.MAX_VALUE);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                session.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
