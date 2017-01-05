package com.epam.repository;


import com.epam.entity.AccidentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * Created by yangyi on 30/12/16.
 */

public interface RoadAccidentRepository extends JpaRepository<AccidentJpaEntity,Long> {

    List<AccidentJpaEntity> findByPoliceForce(String policeForce);
    long countByPoliceForce(String policeForce);

}
