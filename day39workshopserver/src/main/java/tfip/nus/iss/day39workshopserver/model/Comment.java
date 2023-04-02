package tfip.nus.iss.day39workshopserver.model;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment {

    private String charId;
    private String comment;

    public String getCharId() {
        return charId;
    }

    public void setCharId(String charId) {
        this.charId = charId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static Document toDocument(Comment c) {
        Document d = new Document();
        d.put("charId", c.getCharId());
        d.put("comment", c.getComment());

        return d;
    }

    public static Comment createComment(Document d) {
        Comment c = new Comment();
        c.setCharId(d.getString("charId"));
        c.setComment(d.getString("comment"));

        return c;
    }

    public static JsonObject toJson(Comment c) {
        return Json.createObjectBuilder()
                .add("charId", c.getCharId())
                .add("comment", c.getComment())

                .build();
    }

}
