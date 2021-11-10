package org.bravo.api.task;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PayloadA extends TaskPayload {

    @JsonProperty("laboratory_name")
    private String laboratoryName;
    private String sfdc;
    private List<Integer> tranches;

    public String getLaboratoryName() {
        return laboratoryName;
    }

    public void setLaboratoryName(String laboratoryName) {
        this.laboratoryName = laboratoryName;
    }

    public String getSfdc() {
        return sfdc;
    }

    public void setSfdc(String sfdc) {
        this.sfdc = sfdc;
    }

    public List<Integer> getTranches() {
        return tranches;
    }

    public void setTranches(List<Integer> tranches) {
        this.tranches = tranches;
    }

}
