package xebiaessentialsslackbot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Card {

    final String name;
    final Category category;
    final String title;
    final String back;
    final String authors;

    Card(String name, Category category, String title, String back) {
        this.name = name;
        this.category = category;
        this.title = title.substring(0, title.length() - 2);
        back = back
                .replaceAll("<li>", "• ")
                .replaceAll("</*p>", "")
                .replaceAll("</*ul>\n?", "")
                .replaceAll("</*li>", "")
                .replaceAll("<blockquote>", ">>> ")
                .replaceAll("</blockquote>", "")
        ;
        Matcher authorsMatcher = Pattern.compile(".*<div class=\"attribution\">(.+)</div>.*", Pattern.DOTALL).matcher(back);
        if (authorsMatcher.matches()) {
            this.authors = authorsMatcher.group(1);
            back = back.replaceAll("(.*)<div class=\"attribution\">.+</div>(.*)", "$1$2");
        } else {
            this.authors = null;
        }

        back = back.replaceAll("\n+$", "");

        this.back = back;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!name.equals(card.name)) return false;
        if (!category.equals(card.category)) return false;
        if (!title.equals(card.title)) return false;
        if (!back.equals(card.back)) return false;
        return authors.equals(card.authors);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + back.hashCode();
        result = 31 * result + authors.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

}
