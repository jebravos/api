package org.bravo.api.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayloadB extends TaskPayload {

    @JsonProperty("laboratory_name")
    private String laboratoryName;
    @JsonProperty("identification_column")
    private String identificationColumn;
    @JsonProperty("input_file")
    private String inputFile;

    public String getLaboratoryName() {
        return laboratoryName;
    }

    public void setLaboratoryName(String laboratoryName) {
        this.laboratoryName = laboratoryName;
    }

    public String getIdentificationColumn() {
        return identificationColumn;
    }

    public void setIdentificationColumn(String identificationColumn) {
        this.identificationColumn = identificationColumn;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }
}
