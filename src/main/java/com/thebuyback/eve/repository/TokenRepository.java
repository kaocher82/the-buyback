package com.thebuyback.eve.repository;

import java.util.List;

import com.thebuyback.eve.domain.Token;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Doctrine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TokenRepository extends MongoRepository<Token,String> {
    List<Token> findByClientId(String clientId);
}
