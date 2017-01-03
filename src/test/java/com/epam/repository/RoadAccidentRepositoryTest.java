package com.epam.repository;

import com.epam.config.JpaConfig;

import com.epam.entity.AccidentJpaEntity;
import com.epam.repository.impl.Accident;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * Created by yangyi on 30/12/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaConfig.class)
public class RoadAccidentRepositoryTest {

    @Autowired
    private RoadAccidentRepository roadAccRepos;

    @Test
    public void testFindRoadAccidents() {
        AccidentJpaEntity  accidentJpaEntity = new AccidentJpaEntity("traffic jam");
        roadAccRepos.save(accidentJpaEntity);
        List<AccidentJpaEntity> accidents = roadAccRepos.findAll();
        assertThat(accidents.size(), is(1));
    }

    @Test
    public void testFindOneAccident() {
        AccidentJpaEntity  accidentJpaEntity1 = new AccidentJpaEntity("traffic jam");
        roadAccRepos.save(accidentJpaEntity1);
        AccidentJpaEntity  accidentJpaEntity2 = new AccidentJpaEntity("many cars");
        AccidentJpaEntity  savedAccidentJpaEntity2 = roadAccRepos.save(accidentJpaEntity2);

        assertNotNull(roadAccRepos.findOne(savedAccidentJpaEntity2.getAccidentId()));


    }
}
