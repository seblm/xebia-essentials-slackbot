package xebiaessentialsslackbot;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CardTest {

    @Test
    public void should_remove_new_lines_in_titles() {
        Card card = new Card("", new Category("", ""), "Start with acceptance criteria\n\n", "");

        assertThat(card.title).isEqualTo("Start with acceptance criteria");
    }

    @Test
    public void should_remove_new_line_in_back() {
        Card card = new Card("", new Category("", ""), "\n\n", "some back\n");

        assertThat(card.back).isEqualTo("some back");
    }

    @Test
    public void should_remove_new_lines_in_back() {
        Card card = new Card("", new Category("", ""), "\n\n", "some back\n\n");

        assertThat(card.back).isEqualTo("some back");
    }

    @Test
    public void should_remove_html_paragraph_in_back() {
        Card card = new Card("", new Category("", ""), "\n\n", "<p>some back</p>\n");

        assertThat(card.back).isEqualTo("some back");
    }

    @Test
    public void should_remove_html_paragraphs_in_back() {
        Card card = new Card("", new Category("", ""), "\n\n", "<p>some</p>\n<p>back</p>\n");

        assertThat(card.back).isEqualTo("some\nback");
    }

    @Test
    public void should_replace_html_ul_and_li_in_back() {
        Card card = new Card("", new Category("", ""), "\n\n", "" +
                "<ul>\n" +
                "<li>Don't document bad code &ndash; rewrite it</li>\n" +
                "<li>Don't repeat the code &ndash; clarify its intent</li>\n" +
                "<li>Document surprises and workarounds</li>\n" +
                "<li>Make every comment count</li>\n" +
                "</ul>\n\n");

        assertThat(card.back).isEqualTo("" +
                "• Don't document bad code &ndash; rewrite it\n" +
                "• Don't repeat the code &ndash; clarify its intent\n" +
                "• Document surprises and workarounds\n" +
                "• Make every comment count");
    }

    @Test
    public void should_extract_authors() {
        Card card = new Card("", new Category("", ""), "\n\n", "" +
                "<blockquote><p>Separate a program into non-overlapping concerns.\n" +
                "<div class=\"attribution\">Edsger W. Dijkstra</div></p></blockquote>\n");

        assertThat(card.authors).isEqualTo("Edsger W. Dijkstra");
        assertThat(card.back).isEqualTo(">>> Separate a program into non-overlapping concerns.");
    }

}
