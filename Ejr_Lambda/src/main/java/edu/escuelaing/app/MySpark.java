package edu.escuelaing.app;

import  static edu.escuelaing.app.HttpServer.get;

import java.io.IOException;

public class MySpark {
    public static void main(String[] args) {
        get("/hello", str -> "Hello " + str);
        try {
            HttpServer.getInstance().start(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
