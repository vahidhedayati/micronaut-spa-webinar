package com.objectcomputing.repository;

import com.objectcomputing.domain.User;
import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.annotation.EntityGraph;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import io.micronaut.data.model.Sort;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.repository.PageableRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, PageableRepository<User, Long> {

 public static String USERS_BY_LOGIN_CACHE = "usersByLogin";

 public static String USERS_BY_EMAIL_CACHE = "usersByEmail";

 Optional<User> findOneByActivationKey(String activationKey);

 List<User> findAllByActivatedFalseAndCreatedDateBefore(Instant dateTime);

 Optional<User> findOneByResetKey(String resetKey);

 Optional<User> findByEmailIgnoreCase(String email);

 User get(Long id);

 @EntityGraph(attributePaths = "authorities")
 Optional<User> findOneById(Long id);

 @EntityGraph(attributePaths = "authorities")
 @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
 Optional<User> findByLogin(String login);

 @EntityGraph(attributePaths = "authorities")
 @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
 Optional<User> findByEmail(String email);

 Page<User> findAll(Pageable pageable);

 Page<User> findAllByLoginNot(String login, Pageable pageable);

 //List<User> findByLoginLikeOrderByAge(String login);

 //List<User> findByLoginLikeOrderByAgeDesc(String login);

 Page<User> findByLoginLike(String login, Pageable pageable);

 List<User> listTop10(Sort sort);

 Slice<User> find(Pageable pageable);

 Slice<User> queryByLoginLike(String login, Pageable pageable);

 Optional<User> findOptionalByLogin(String login);

 List<User> findAllByLogin(String login);

 void update(@Id Long id, Instant createdDate);



}


