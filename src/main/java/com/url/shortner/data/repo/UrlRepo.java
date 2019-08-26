package com.url.shortner.data.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.url.shortner.entity.UrlEntity;

/**
 * spring repository to deal with url_entity table
 * @author Charu Agarwal
 *
 */
public interface UrlRepo extends CrudRepository<UrlEntity, Long> {
	@Query(value = "select max(id) from url_entity", nativeQuery = true)
    public Long findMaxId();
}