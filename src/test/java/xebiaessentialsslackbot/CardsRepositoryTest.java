package xebiaessentialsslackbot;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CardsRepositoryTest {

    @Test
    void should_load_categories() {
        Optional<CardsRepository> maybeRepository = CardsRepository.load();

        assertThat(maybeRepository).hasValueSatisfying(repository -> assertThat(repository.categories)
                .hasSize(5)
                .containsOnly(
                        new Category("craftsmanship", "#F80068"),
                        new Category("collaboration", "#FC7A25"),
                        new Category("realisation", "#107FD5"),
                        new Category("testing", "#6DC726"),
                        new Category("other", "#4C2F5C")
                )
        );
    }

    @Test
    void should_load_cards() {
        Optional<CardsRepository> maybeRepository = CardsRepository.load();

        assertThat(maybeRepository).hasValueSatisfying(repository -> assertThat(repository.cards)
                .hasSize(74)
        );
    }

}
