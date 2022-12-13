package ru.osipov;

import java.util.List;

public class Request {
    final List<String> validMethod = List.of("GET", "POST", "PUT", "DELETE", "AUTH");
    private final String method;
    private final String path;
    private String requestBody;

    public Request(String method, String path){
        this.method = method;
        this.path = path;
    }

    public Request(String method, String path, String requestBody){
        this.method = method;
        this.path = path;
        this.requestBody = requestBody;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
