package com.epam.repository.impl;



/**
 * Created by yangyi on 3/1/17.
 */
public class Accident {
    public Accident() {

    }
    public Accident(String policeForce) {
        this.policeForce = policeForce;
    }

    private Long accidentId;
    private String policeForce;

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
