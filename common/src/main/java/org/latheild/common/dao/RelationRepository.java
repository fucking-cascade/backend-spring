package org.latheild.common.dao;

import org.latheild.common.domain.Relation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RelationRepository extends MongoRepository<Relation, String> {
}
