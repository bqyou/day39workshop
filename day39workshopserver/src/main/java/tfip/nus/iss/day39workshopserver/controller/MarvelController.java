package tfip.nus.iss.day39workshopserver.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import tfip.nus.iss.day39workshopserver.model.Comment;
import tfip.nus.iss.day39workshopserver.model.Hero;
import tfip.nus.iss.day39workshopserver.repo.MarvelRepo;

@Controller
@RequestMapping(path = "/api/characters", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MarvelController {

    @Autowired
    private MarvelRepo marvelRepo;

    @GetMapping
    @ResponseBody
    public ResponseEntity<String> getHeroes(@RequestParam String searchPhrase,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset)
            throws IOException {
        List<Hero> heroList = marvelRepo.getHeroesList(searchPhrase, limit, offset).get();
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Hero hero : heroList) {
            arrBuilder.add(hero.toJson());
        }
        JsonArray result = arrBuilder.build();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping(path = "/{charId}")
    @ResponseBody
    public ResponseEntity<String> getDetails(@PathVariable(required = true) String charId) throws IOException {
        String result;

        // result = marvelRepo.getHeroStringFromRedis(charId);
        // System.out.println("REDIS HERO>>>>" + result);
        // if (result == null) {
        Hero hero = marvelRepo.getHero(charId).get();
        result = hero.toJson().toString();
        // String heroId = Integer.toString(hero.getId());
        // marvelRepo.save(heroId, result);

        // System.out.println("API HERO>>>>" + result);
        // }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @PostMapping(path = "/{charId}")
    @ResponseBody
    public ResponseEntity<String> postComment(@PathVariable(required = true) String charId,
            @RequestBody Comment comment) {
        Comment newComment = new Comment();
        newComment.setCharId(charId);
        newComment.setComment(comment.getComment());
        marvelRepo.addComment(newComment);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("");
    }

    @GetMapping(path = "/comments/{charId}")
    @ResponseBody
    public ResponseEntity<String> getComments(@PathVariable(required = true) String charId) {
        List<Comment> comments = marvelRepo.getComments(charId);
        List<JsonObject> jsons = comments.stream().map(v -> Comment.toJson(v)).toList();
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (JsonObject j : jsons) {
            arrBuilder.add(j);
        }
        JsonArray arr = arrBuilder.build();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(arr.toString());

    }

}
