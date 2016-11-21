### xebia-essentials-slackbot

A slack bot that show you a random xebia-essentials card each day.

#### Rules once integrated

Invite **xebia-essentials** bot to any channel. If someone talk in the same message about _card_ and _day_ (like _what
is the card of the day?_) then **xebia-essentials** bot will respond to you with some nice card.

If you want to change the card of the day and the other ones, just send a private message to **xebia-essentials** bot
with _shuffle_ word into it. **xebia-essentials** bot will shuffle all cards and will show you another one once you'll
ask him another time what's the card of the day.

#### How to run it?

If you have java and maven on your host:

    mvn clean install

Else:

    docker run --interactive --tty --workdir=/opt/maven --volume ${PWD}:/opt/maven --volume ${HOME}/.m2:/root/.m2 \
      maven:latest mvn clean install

Then

    docker build --tag=xebia-essentials-slackbot .
    docker run [--interactive --tty|--detach] xebia-essentials-slackbot <API Token>
