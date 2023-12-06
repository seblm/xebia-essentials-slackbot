package xebiaessentialsslackbot;

import com.ullink.slack.simpleslackapi.SlackAttachment;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Bot {

    public static void main(String[] args) {
        String authToken = System.getenv("AUTH_TOKEN");

        if (authToken == null && args.length > 0) {
            authToken = args[0];
        }

        if (authToken == null) {
            System.err.println("no AUTH_TOKEN");
            System.exit(-1);
        }

        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(authToken);

        Optional<CardsRepository> maybeCardsRepository = CardsRepository.load();
        if (maybeCardsRepository.isEmpty()) {
            System.err.println("unable to load cards");
            System.exit(-1);
        }

        CardsRepository repository = maybeCardsRepository.get();
        List<Card> shuffledCards = new ArrayList<>(repository.cards);
        Collections.shuffle(shuffledCards);
        LocalDate startedDate = LocalDate.now();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                session.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        try {
            session.connect();
            session.addMessagePostedListener((event, s) -> {
                if (event.getSender().isBot()) {
                    return;
                }
                if (event.getMessageContent().contains("card") && event.getMessageContent().contains("day")) {
                    Card cardOfTheDay = shuffledCards.get(Period.between(startedDate, LocalDate.now()).getDays() % shuffledCards.size());
                    SlackAttachment slackAttachment = SlackAttachment.builder()
                            .title(cardOfTheDay.title)
                            .fallback(cardOfTheDay.title)
                            .text(cardOfTheDay.back)
                            .pretext("card of the day: " + cardOfTheDay.name)
                            .build();
                    slackAttachment.setColor(cardOfTheDay.category.colour);
                    slackAttachment.setTitleLink("http://essentials.xebia.com/" + cardOfTheDay.name);
                    if (cardOfTheDay.authors != null) {
                        slackAttachment.setAuthorName(cardOfTheDay.authors);
                    }
                    slackAttachment.addMarkdownIn("text");
                    session.sendMessage(event.getChannel(), "", slackAttachment);
                } else if (event.getMessageContent().contains("shuffle")
                        && (event.getMessageContent().startsWith("<@" + session.sessionPersona().getId() + ">")
                        || event.getChannel().isDirect())) {
                    Collections.shuffle(shuffledCards);
                    session.sendMessage(event.getChannel(), "shuffle done, sir");
                }
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
