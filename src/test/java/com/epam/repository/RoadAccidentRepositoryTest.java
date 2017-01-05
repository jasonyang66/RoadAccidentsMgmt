package com.epam.repository;

import com.epam.config.JpaConfig;

import com.epam.entity.AccidentJpaEntity;
import com.epam.repository.impl.Accident;
import org.aspectj.lang.annotation.Before;
import org.hibernate.criterion.CriteriaQuery;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;

/**
 * Created by yangyi on 30/12/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaConfig.class)
public class RoadAccidentRepositoryTest {

    @Autowired
    private RoadAccidentRepository roadAccRepos;

//    @Autowired
//    private EntityManager em;

    @Test
    public void testFindRoadAccidents() {
        AccidentJpaEntity  accidentJpaEntity = new AccidentJpaEntity("traffic jam");
        roadAccRepos.save(accidentJpaEntity);
        List<AccidentJpaEntity> accidents = roadAccRepos.findAll();
        assertThat(accidents.size(), is(1));
    }

    @Test
    public void testFindOneAccident() {
        AccidentJpaEntity  accidentJpaEntity2 = new AccidentJpaEntity("many cars");
        AccidentJpaEntity  savedAccidentJpaEntity2 = roadAccRepos.save(accidentJpaEntity2);
        assertNotNull(savedAccidentJpaEntity2);
        Long accidentId = savedAccidentJpaEntity2.getAccidentId();
        String policeForce = savedAccidentJpaEntity2.getPoliceForce();
        AccidentJpaEntity foundAccident = roadAccRepos.findOne(accidentId);
        assertNotNull(foundAccident);
        Assert.assertThat(foundAccident.getPoliceForce(), equalTo(policeForce));
    }



    @Test
    public void testAccidentCount() {
        init();
        long count = roadAccRepos.count();
        assertThat(count, is(9L));

    }

    @Test
    public void testAccidentCountByPoliceForce() {
        init();
        long count = roadAccRepos.countByPoliceForce("Too fat man");
        assertThat(count, is(2L));
    }

    @Test
    public void testFindAccidentListByPoliceForce() {
        init();
        List<AccidentJpaEntity> accidentList = roadAccRepos.findByPoliceForce("Road killer");
        assertThat(accidentList.size(), is(3));
        List<AccidentJpaEntity> accidentList2 = roadAccRepos.findByPoliceForce("Too fat man");
        assertThat(accidentList2.size(), is(2));

    }

    private void init() {
        if(isAccidentCountGreatZero()) {
            roadAccRepos.deleteAll();
        }
        Arrays.asList("No license,Road killer,Drinker driver".split(","))
                .forEach(policeForce -> roadAccRepos.save(new AccidentJpaEntity(policeForce)));
        Arrays.asList("Too rocker ,Road killer,Too fat man".split(","))
                .forEach(policeForce -> roadAccRepos.save(new AccidentJpaEntity(policeForce)));
        Arrays.asList("No license,Road killer,Too fat man".split(","))
                .forEach(policeForce -> roadAccRepos.save(new AccidentJpaEntity(policeForce)));

    }

    private boolean isAccidentCountGreatZero() {
        return roadAccRepos.count() > 0;
    }

//    @Test
//    public void testAccCountGroupByAccYearAndWeathCond() {
//        AccidentJpaEntity  accidentJpaEntity1 = new AccidentJpaEntity("traffic jam","1","2017");
//        AccidentJpaEntity  accidentJpaEntity2 = new AccidentJpaEntity("traffic jam","1","2017");
//        AccidentJpaEntity  accidentJpaEntity3 = new AccidentJpaEntity("traffic jam","1","2016");
//        roadAccRepos.save(accidentJpaEntity1);
//        roadAccRepos.save(accidentJpaEntity2);
//        roadAccRepos.save(accidentJpaEntity3);
//
//        CriteriaBuilder builder =   em.getCriteriaBuilder();
//        CriteriaQuery<AccidentJpaEntity> query = builder.createQuery(AccidentJpaEntity.class);
//    }

}
