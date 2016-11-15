package xebiaessentialsslackbot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class CardsRepository {

    final Collection<Category> categories;

    final List<Card> cards;

    private CardsRepository(Collection<Category> categories, List<Card> cards) {
        this.categories = categories;
        this.cards = cards;
    }

    static Optional<CardsRepository> load() {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject parse = (JSONObject) jsonParser.parse(new InputStreamReader(CardsRepository.class.getResourceAsStream("api.json")));
            Map<String, Category> categories = new HashMap<>();
            for (Object c : ((JSONArray) parse.get("categories"))) {
                JSONObject category = (JSONObject) c;
                String name = (String) category.get("name");
                categories.put(name, new Category(name, (String) category.get("colour")));
            }
            List<Card> cards = new ArrayList<>();
            for (Object c : ((JSONArray) parse.get("cards"))) {
                JSONObject card = (JSONObject) c;
                cards.add(new Card(
                        (String) card.get("name"),
                        categories.get(card.get("category")),
                        (String) card.get("title"),
                        (String) card.get("back")
                ));
            }
            return Optional.of(new CardsRepository(categories.values(), cards));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
