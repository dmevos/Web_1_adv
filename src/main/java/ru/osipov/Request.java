package ru.osipov;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Request {
    private final String method;
    private final String path;
    private final String protocolVersion;
    private final List<String> headers;
    private final byte[] body;


    private Request(String method, String path, String protocolVersion, List<String> headers, byte[] body) {
        this.method = method;
        this.path = path;
        this.protocolVersion = protocolVersion;
        this.headers = headers;
        this.body = body;
    }

    public static Request parse(BufferedReader in) throws IOException {
        // read only request line for simplicity
        // must be in form GET /path HTTP/1.1
        final var requestLine = in.readLine();
        final var parts = requestLine.split(" ");

        if (parts.length != 3) return null;

        return new Request(parts[0], parts[1], parts[2], null, null);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

}
