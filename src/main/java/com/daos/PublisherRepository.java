package com.daos;

import com.models.Publisher;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//EXAMPLES: http://docs.spring.io/spring-data/jpa/docs/current/reference/html/
@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Long> {

  /*If not using spring repo, we can use EntityManager like this:
   @PersistenceContext
   private EntityManager entityManager;
   */
  Publisher findByUsername(String username);

  @Override
  List<Publisher> findAll();

}
