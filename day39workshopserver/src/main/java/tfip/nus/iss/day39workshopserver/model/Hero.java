package tfip.nus.iss.day39workshopserver.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

public class Hero implements Serializable {

    private Integer id;
    private String name;
    private String imageUrl;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Hero toHero(JsonObject o) {
        Hero hero = new Hero();
        hero.setId(o.getInt("id"));
        hero.setName(o.getString("name"));
        hero.setDescription(o.getString("description"));
        JsonObject thumbnail = o.getJsonObject("thumbnail");
        hero.setImageUrl(thumbnail.getString("path") + "." + thumbnail.getString("extension"));
        return hero;
    }

    public static List<Hero> toHeroList(String o) throws IOException {
        List<Hero> heroes = new LinkedList<Hero>();
        try (
                InputStream is = new ByteArrayInputStream(o.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject jsonObject = r.readObject();
            JsonObject data = (JsonObject) jsonObject.get("data");
            heroes = data.getJsonArray("results")
                    .stream()
                    .map(v -> (JsonObject) v)
                    .map(v -> Hero.toHero(v))
                    .toList();
        }
        return heroes;
    }

    public JsonObject toJson() {
        JsonObjectBuilder j = Json.createObjectBuilder()
                .add("id", getId())
                .add("name", getName())
                .add("imageUrl", getImageUrl())
                .add("description", getDescription());
        return j.build();
    }

    public static Hero createForCache(String json) throws IOException {
        Hero c = new Hero();
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            c.setId(o.getJsonNumber("id").intValue());
            c.setName(o.getString("name"));
            c.setDescription(o.getString("description"));
            c.setImageUrl(o.getString("imageUrl"));
        }
        return c;
    }

}
