package org.bravo.api.entity;

public class Algorithm {

    private String id;
    private String url;
    private Short port;

    public Algorithm() {
    }

    public Algorithm(String id, String url, Short port) {
        this.id = id;
        this.url = url;
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Short getPort() {
        return port;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPort(Short port) {
        this.port = port;
    }

    public String getCompleteUrl(){
        return this.url + ":" + port;
    }

//    public Set<Task> getTasks() {
//        return tasks;
//    }
//
//    public void setTasks(Set<Task> tasks) {
//        this.tasks = tasks;
//    }
}

