package tfip.nus.iss.day39workshopserver.config;

import org.springframework.context.annotation.Configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class RedisConfig {

    // @Value("${spring.redis.host}")
    // private String redisHost;

    // @Value("${spring.redis.port}")
    // private Optional<Integer> redisPort;

    // @Value("${spring.redis.username}")
    // private String redisUsername;

    // @Value("${spring.redis.password}")
    // private String redisPassword;

    // @Bean
    // @Scope("singleton")
    // public RedisTemplate<String, String> redisTemplate() {
    // final RedisStandaloneConfiguration config = new
    // RedisStandaloneConfiguration();
    // config.setHostName(redisHost);
    // config.setPort(redisPort.get());
    // if (!redisUsername.isEmpty() && !redisPassword.isEmpty()) {
    // config.setUsername(redisUsername);
    // config.setPassword(redisPassword);
    // }
    // config.setDatabase(0);
    // final JedisClientConfiguration jedisClient =
    // JedisClientConfiguration.builder().build();
    // final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config,
    // jedisClient);
    // jedisFac.afterPropertiesSet();
    // RedisTemplate<String, String> redisTemplate = new RedisTemplate<String,
    // String>();
    // redisTemplate.setConnectionFactory(jedisFac);
    // redisTemplate.setKeySerializer(new StringRedisSerializer());
    // redisTemplate.setHashKeySerializer(redisTemplate.getKeySerializer());
    // redisTemplate.setValueSerializer(redisTemplate.getKeySerializer());
    // redisTemplate.setHashValueSerializer(redisTemplate.getKeySerializer());
    // return redisTemplate;
    // }

    @Value("${mongo.url}")
    private String mongoUrl;

    @Bean
    public MongoTemplate createComments() {
        MongoClient client = MongoClients.create(mongoUrl);
        return new MongoTemplate(client, "MarvelComments");
    }

}