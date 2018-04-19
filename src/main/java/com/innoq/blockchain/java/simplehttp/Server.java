package com.innoq.blockchain.java.simplehttp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innoq.blockchain.java.common.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Server {
    static ObjectMapper om = new ObjectMapper();

    private static void writeObjectAsJson(HttpExchange exc, Object o) throws IOException {
        byte [] response = om.writeValueAsBytes(o);
        exc.sendResponseHeaders(200, response.length);
        OutputStream os = exc.getResponseBody();
        os.write(response);
        os.close();
    }

    private static void writeError(HttpExchange exc, String s) throws IOException {
        byte [] response = s.getBytes();
        exc.sendResponseHeaders(500, response.length);
        OutputStream os = exc.getResponseBody();
        os.write(response);
        os.close();
    }
    public static void main(String[] args) throws Exception {

        MiningService miner = new MiningService("000");
        BlockChain bc = new BlockChainService(miner);
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exc) throws IOException {
                writeObjectAsJson(exc, bc.getStatus());
            }
        });

        server.createContext("/blocks", new HttpHandler() {
            @Override
            public void handle(HttpExchange exc) throws IOException {
                writeObjectAsJson(exc, bc.getBlockChain());
            }
        });

        server.createContext("/mine", new HttpHandler() {
            @Override
            public void handle(HttpExchange exc) throws IOException {
                try {
                    writeObjectAsJson(exc, bc.mineBlock());
                } catch (Exception e) {
                    e.printStackTrace();
                    writeError(exc, e.getMessage());
                }
            }
        });

        server.createContext("/transactions", new HttpHandler() {
            @Override
            public void handle(HttpExchange exc) throws IOException {
                if (exc.getRequestMethod().equalsIgnoreCase("POST")) {
                    Payload p = om.readValue(exc.getRequestBody(), Payload.class);
                    bc.addTransaction(p);
                }
            }
        });
        System.out.println("Starting server on port 8080");
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
