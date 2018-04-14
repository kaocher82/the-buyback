package com.thebuyback.eve.repository;

import java.util.List;
import java.util.Set;

import com.thebuyback.eve.domain.TypeBuybackRate;
import com.thebuyback.eve.domain.TypeIngredients;
import com.thebuyback.eve.domain.enumeration.TypeCategory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the TypeBuybackRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeIngredientsRepository extends MongoRepository<TypeIngredients, String> {
    List<TypeIngredients> findAllByTypeIdIn(Set<Long> typeIds);
}
