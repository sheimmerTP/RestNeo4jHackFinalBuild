package com.travelport.restneohack.model.repositories;

import com.travelport.restneohack.model.domain.World;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface WorldRepository extends GraphRepository<World> {}
