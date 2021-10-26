package org.bravo.api.model;

public class AlgoResultResponse {

    private String id;
    private String result;


    public AlgoResultResponse() {
    }

    public AlgoResultResponse(String id, String result) {
        this.id = id;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
