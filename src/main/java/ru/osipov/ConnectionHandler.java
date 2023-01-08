package ru.osipov;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionHandler {
    private final Socket socket;
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Handler>> handlers;

    public ConnectionHandler(Socket socket, ConcurrentHashMap<String, ConcurrentHashMap<String, Handler>> handlers) {
        this.socket = socket;
        this.handlers = handlers;
    }

    public void handle() {
        try {
            final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            final var out = new BufferedOutputStream(socket.getOutputStream());

            var request = Request.parse(in);

            if (request ==null) return;

            var pathHandlers = handlers.get(request.getMethod());

            if (!handlers.containsKey(request.getMethod()) || !pathHandlers.containsKey(request.getPath())) {
                responseBad(out, "404", "Not Found");
                return;
            }

            var handler = pathHandlers.get(request.getPath());
            try {
                handler.handle(request, out);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                responseBad(out, "500", "Internal Server Error");
            }

        } catch (IOException e) {
            System.out.println("Бебебе");
            e.printStackTrace();
        }
    }

    private void responseBad(BufferedOutputStream out, String responseCode, String responseStatus) throws IOException {
        out.write((
                "HTTP/1.1 " + responseCode + " " + responseStatus + "\r\n" +
                        "Content-Length: 0\r\n" +
                        "Connection: close\r\n" +
                        "\r\n"
        ).getBytes());
        out.flush();
    }
}
