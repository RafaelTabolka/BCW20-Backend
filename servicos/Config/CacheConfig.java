package com.SoulCode.servicos.Config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

// É uma classe que define configurações padrões
@Configuration
public class CacheConfig {

    // serializador transforma arquivos
    private final RedisSerializationContext.SerializationPair<Object> serializationPair =
            RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()); // pega o elemento do java e transforma em redis e pega o elemento redis e transforma em java

    @Bean
    public RedisCacheConfiguration cacheConfiguration() { // customizar a configuração padrão do redis cache
        return RedisCacheConfiguration
                .defaultCacheConfig()  // customizar informações padrões
                .entryTtl(Duration.ofMinutes(5)) // todos os caches terão 5 minutos por padrão (tempo de vida)
                .disableCachingNullValues() // não salva valores nulos
                .serializeValuesWith(serializationPair); // converte do redis para json e vice-versa
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder.withCacheConfiguration("clientesCache",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(30))
                                .serializeValuesWith(serializationPair))
                .withCacheConfiguration("chamadosCache",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(10))
                                .serializeValuesWith(serializationPair))
                .withCacheConfiguration("cacheUser",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(5)).serializeValuesWith(serializationPair))
                .withCacheConfiguration("authCache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(60)).serializeValuesWith(serializationPair));
    }
}









