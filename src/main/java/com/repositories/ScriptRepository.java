package com.repositories;

import com.models.AuthUser;
import com.models.Script;
import java.util.Collection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 EXAMPLES: http://docs.spring.io/spring-data/jpa/docs/current/reference/html/

 You probably won't need this, but here is an example of how to add custom methods.
 http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.custom-implementations

 You can also forgo the use of spring data and create your own repository class.
 Just create a class similar to this (no need to extend anything), and use EntityManager to create your query methods.
 If you choose this path, you may want to look into QueryDsl: http://www.querydsl.com/ for type safe queries.
 */
@Repository
public interface ScriptRepository extends CrudRepository<Script, Long> {

	/* If not using spring data, we can use EntityManager like this:
   @PersistenceContext
   private EntityManager entityManager;
	 */
	Script findByNameAndOwner(String scriptName, AuthUser owner);

	Collection<Script> findAllByOwner(AuthUser owner);

}
