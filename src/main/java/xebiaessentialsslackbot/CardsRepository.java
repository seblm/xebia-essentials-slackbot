package xebiaessentialsslackbot;

import com.google.gson.Gson;
import xebiaessentialsslackbot.api.Api;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

class CardsRepository {

    final Collection<Category> categories;

    final List<Card> cards;

    private CardsRepository(Collection<Category> categories, List<Card> cards) {
        this.categories = categories;
        this.cards = cards;
    }

    static Optional<CardsRepository> load() {
        Gson jsonParser = new Gson();
        try (InputStreamReader reader = new InputStreamReader(requireNonNull(CardsRepository.class.getResourceAsStream("api.json")))) {
            Api api = jsonParser.fromJson(reader, Api.class);
            Map<String, Category> categories = api.categories.stream().collect(toMap(c -> c.name, c -> new Category(c.name, c.colour)));
            return Optional.of(new CardsRepository(
                    categories.values(),
                    api.cards.stream().map(c -> new Card(c.name, categories.get(c.category), c.title, c.back)).collect(toList())
            ));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
