package com.objectcomputing.repository;

import com.objectcomputing.domain.User;
import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.annotation.EntityGraph;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

 public static String USERS_BY_LOGIN_CACHE = "usersByLogin";

 public static String USERS_BY_EMAIL_CACHE = "usersByEmail";

 public abstract Optional<User> findOneByActivationKey(String activationKey);

 public abstract List<User> findAllByActivatedFalseAndCreatedDateBefore(Instant dateTime);

 public abstract Optional<User> findOneByResetKey(String resetKey);

 public abstract Optional<User> findOneByEmailIgnoreCase(String email);

 Optional<User> findByLogin(String login);

 User get(Long id);

 @EntityGraph(attributePaths = "authorities")
 public abstract Optional<User> findOneById(Long id);

 @EntityGraph(attributePaths = "authorities")
 @Cacheable(cacheNames = "usersByLogin")
 public abstract Optional<User> findOneByLogin(String login);

 @EntityGraph(attributePaths = "authorities")
 @Cacheable(cacheNames = "usersByEmail")
 public abstract Optional<User> findOneByEmail(String email);

 public abstract Page<User> findAllByLoginNot(String login, Pageable pageable);

 public abstract void update(@Id Long id, Instant createdDate);



}


