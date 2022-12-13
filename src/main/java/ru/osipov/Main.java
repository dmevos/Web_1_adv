package ru.osipov;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();

//        server.addHandler("GET", "/messages", (request, responseStream) -> server.responseBad(responseStream, "404", "Not Found");
//        server.addHandler("POST", "/messages", (request, responseStream) -> server.responseBad(responseStream, "503", "Service Unavailable"));

        server.addHandler("POST", "/formsPOST.html", (request, responseStream) -> server.responseBad(responseStream, "501", "Not Implemented"));

        server.addHandler("GET", "/", ((request, outputStream) -> server.defaultHandler(outputStream, "index.html")));
        server.start();
    }
}
