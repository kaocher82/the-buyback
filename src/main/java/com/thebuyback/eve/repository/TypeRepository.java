package com.thebuyback.eve.repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.thebuyback.eve.domain.Type;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TypeRepository extends MongoRepository<Type, String> {
    Optional<Type> findByTypeId(long typeId);
    Optional<Type> findByTypeName(String typeName);

    Optional<Type> findByTypeIdAndGroupName(long typeId, String groupName);

    Stream<Type> findByTypeIdIn(Set<Long> typeIds);

    Stream<Type> findByGroupIdIn(Set<Long> groupIds);
}
