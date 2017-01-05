package com.epam.entity;

import com.epam.repository.impl.Accident;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by yangyi on 30/12/16.
 */
@Entity
public class AccidentJpaEntity implements Serializable {

    public AccidentJpaEntity(Accident accident) {
        this.accidentId = accident.getAccidentId();
        this.policeForce = accident.getPoliceForce();
    }

    public AccidentJpaEntity(){}

    public AccidentJpaEntity(String policeForce){
        this.policeForce = policeForce;
    }

    public AccidentJpaEntity(String policeForce, String weatherCondition, String accidentYear) {
        this.policeForce = policeForce;
        this.weatherCondition = weatherCondition;
        this.accidentYear = accidentYear;
    }
    @Id
    @GeneratedValue
    private Long accidentId;
    private String policeForce;

    private String weatherCondition;
    private String accidentYear;

    public Accident toAccident() {
        Accident accident = new Accident();
        accident.setAccidentId(accidentId);
        accident.setPoliceForce(policeForce);

        return accident;
    }

    public Long getAccidentId() {
        return accidentId;
    }

    public void setAccidentId(Long accidentId) {
        this.accidentId = accidentId;
    }

    public String getPoliceForce() {
        return policeForce;
    }

    public void setPoliceForce(String policeForce) {
        this.policeForce = policeForce;
    }
}
