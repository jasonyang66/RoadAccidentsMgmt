package com.epam.repository;


import com.epam.entity.AccidentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Created by yangyi on 30/12/16.
 */

public interface RoadAccidentRepository extends JpaRepository<AccidentJpaEntity,Long> {

    //Accident findOne(Long id);
}
