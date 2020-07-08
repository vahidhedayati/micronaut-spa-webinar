package com.objectcomputing.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;


import com.objectcomputing.domain.Authority;
import com.objectcomputing.domain.User;
import com.objectcomputing.repository.UserRepository;
import com.objectcomputing.util.MyApplicationProperties;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Factory;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.ehcache.jsr107.EhcacheCachingProvider;

import javax.cache.CacheManager;
import javax.inject.Singleton;

@Factory
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(MyApplicationProperties jHipsterProperties) {
        MyApplicationProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());

        // TODO add caffiene configuration
    }

    @Singleton
    public CacheManager cacheManager(ApplicationContext applicationContext) {
        CacheManager cacheManager = new EhcacheCachingProvider().getCacheManager(
            null, applicationContext.getClassLoader(), new Properties());
        customizeCacheManager(cacheManager);
        return cacheManager;
    }

    private void customizeCacheManager(CacheManager cm) {
        createCache(cm, UserRepository.USERS_BY_LOGIN_CACHE);
        createCache(cm, UserRepository.USERS_BY_EMAIL_CACHE);
        createCache(cm, User.class.getName());
        createCache(cm, Authority.class.getName());
        createCache(cm,User.class.getName() + ".authorities");
        //createCache(cm,BankAccount.class.getName());
        //createCache(cm,Label.class.getName());
        //createCache(cm,Operation.class.getName());
        //createCache(cm,BankAccount.class.getName() + ".operations");
        //createCache(cm,Label.class.getName() + ".operations");
        //createCache(cm,Operation.class.getName() + ".labels");
        // jhipster-needle-ehcache-add-entry
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
