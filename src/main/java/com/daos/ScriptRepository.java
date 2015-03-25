package com.daos;

import com.models.Publisher;
import com.models.Script;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//EXAMPLES: http://docs.spring.io/spring-data/jpa/docs/current/reference/html/
@Repository
public interface ScriptRepository extends CrudRepository<Script, Long> {

  /*If not using spring repo, we can use EntityManager like this:
   @PersistenceContext
   private EntityManager entityManager;
   */
  Script findByNameAndPublisher(String username, Publisher publisher);

  List<Script> findAllByPublisher(Publisher publisher);

}
