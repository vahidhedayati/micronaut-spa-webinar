package com.objectcomputing.repository;


import com.objectcomputing.domain.Authority;
import com.sun.istack.Nullable;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


/**
 * Micronaut Data repository for the {@link Authority} entity.
 */
@Repository
public interface AuthorityRepository extends CrudRepository<Authority, String> {

    Optional<Authority> findByName(String name);

    List<Authority> findAll();

}
