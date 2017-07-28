package com.my.test.repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.NoRepositoryBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@NoRepositoryBean
public class BaseRepository {

	@PersistenceContext(unitName = "pcard")
	protected EntityManager entityManager;

	private static Logger logger = LoggerFactory.getLogger(BaseRepository.class);

	
}
