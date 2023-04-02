package tfip.nus.iss.day39workshopserver.repo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import tfip.nus.iss.day39workshopserver.model.Comment;
import tfip.nus.iss.day39workshopserver.model.Hero;
import tfip.nus.iss.day39workshopserver.model.MD5Hash;

@Repository
public class MarvelRepo {

    // @Autowired
    // private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${marvel.public.api}")
    private String publicAPI;

    @Value("${marvel.private.api}")
    private String privateAPI;

    private static final String MARVEL_URL = "https://gateway.marvel.com/v1/public/characters";

    public Optional<List<Hero>> getHeroesList(String searchPhrase, Integer limit, Integer offset)
            throws IOException {

        MD5Hash md5Hash = MD5Hash.getMD5Hash(privateAPI + publicAPI);
        String marvelCharactersUrl = UriComponentsBuilder.fromUriString(MARVEL_URL)
                .queryParam("nameStartsWith", searchPhrase.replaceAll(" ", "+"))
                .queryParam("limit", limit)
                .queryParam("offset", offset)
                .queryParam("ts", md5Hash.getTimeStamp())
                .queryParam("apikey", publicAPI)
                .queryParam("hash", md5Hash.getHash())
                .toUriString();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        resp = template.getForEntity(marvelCharactersUrl, String.class);

        List<Hero> heroList = Hero.toHeroList(resp.getBody());
        if (heroList != null)
            return Optional.of(heroList);
        return Optional.empty();
    }

    // public void save(String id, String hero) {
    // redisTemplate.opsForValue().set(id, hero);
    // long currentTime = Instant.now().getMillis();
    // Date afterAdding60Mins = new Date(currentTime + (60 * 60 * 1000));
    // redisTemplate.expireAt(id, afterAdding60Mins);
    // }

    // public String getHeroStringFromRedis(String id) {
    // String body = redisTemplate.opsForValue().get(id);
    // return body;
    // }

    public Optional<Hero> getHero(String characterId) throws IOException {
        MD5Hash md5Hash = MD5Hash.getMD5Hash(privateAPI + publicAPI);
        String characterUrl = UriComponentsBuilder.fromUriString(MARVEL_URL + "/" + characterId)
                .queryParam("ts", md5Hash.getTimeStamp())
                .queryParam("apikey", publicAPI)
                .queryParam("hash", md5Hash.getHash())
                .toUriString();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        resp = template.getForEntity(characterUrl, String.class);
        List<Hero> heroList = Hero.toHeroList(resp.getBody());
        Hero hero = heroList.get(0);
        if (hero != null)
            return Optional.of(hero);
        return Optional.empty();
    }

    public void addComment(Comment c) {
        mongoTemplate.insert(Comment.toDocument(c), "comments");
    }

    public List<Comment> getComments(String id) {
        Criteria c = Criteria.where("charId").is(id);
        Query q = new Query().addCriteria(c).limit(10);
        List<Document> docs = mongoTemplate.find(q, Document.class, "comments");
        return docs.stream().map(v -> Comment.createComment(v)).toList();
    }

}
