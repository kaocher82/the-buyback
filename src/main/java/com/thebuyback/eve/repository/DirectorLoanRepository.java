package com.thebuyback.eve.repository;

import com.thebuyback.eve.domain.DirectorLoan;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorLoanRepository extends MongoRepository<DirectorLoan, String> {
}
