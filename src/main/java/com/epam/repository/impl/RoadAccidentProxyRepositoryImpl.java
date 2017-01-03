package com.epam.repository.impl;

import com.epam.entity.AccidentJpaEntity;
import com.epam.repository.AccidentRepository;
import com.epam.repository.RoadAccidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangyi on 30/12/16.
 */
@Repository
public class RoadAccidentProxyRepositoryImpl implements AccidentRepository {
    @Autowired
    private RoadAccidentRepository repository;

    public Accident save(Accident acc) {
        AccidentJpaEntity jpaEntity = new AccidentJpaEntity(acc);
        AccidentJpaEntity savedEntity = repository.save(jpaEntity);
        return savedEntity.toAccident();

    }

    public List<Accident> all() {
        //List<AccidentJpaEntity> entities = repository.findAll();
        return null;
    }

}


