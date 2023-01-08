package ru.osipov;

import java.io.*;
import java.net.ServerSocket;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final static int THREAD_NUMBER = 64;
    private ExecutorService executor;
    private ServerSocket serverSocket;
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Handler>> handlers = new ConcurrentHashMap<>();

    //Запуск сервера
    public void listen(int port) {
        try {
            serverSocket = new ServerSocket(port);
            log("Сервер стартовал!");
        } catch (IOException e) {
            log("[ERROR] Ошибка запуска сервера: " + e.getMessage());
        }
        executor = Executors.newFixedThreadPool(THREAD_NUMBER);
        listenConnection();
    }

    public void listenConnection() {
        while (true) {
            try {
                final var socket = serverSocket.accept();
                log("[INFO] Новое подключение: " + socket.getRemoteSocketAddress());
                executor.submit(() -> new ConnectionHandler(socket, handlers).handle());
            } catch (IOException e) {
                log("Косяк с новым подключенинем:   " + e.getMessage());
            }
        }
    }

    public void addHandler(String method, String path, Handler handler) {
        if (!handlers.containsKey(method)) handlers.put(method, new ConcurrentHashMap<>());

        handlers.get(method).put(path, handler);
    }

    //Логирование сообщений
    private void log(String message) {
        System.out.println(new Timestamp(System.currentTimeMillis()) + ": " + message);
    }
}