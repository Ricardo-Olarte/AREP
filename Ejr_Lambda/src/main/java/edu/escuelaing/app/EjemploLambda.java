package edu.escuelaing.app;

import java.util.*;
import java.lang.*;

public class EjemploLambda {
    static Map<String, Servicio1Param> servicios = new HashMap<String, Servicio1Param>();

    public static void main(String[] args) {
        System.out.println("Inicio");

        registrar("/hello", str -> "Hello " + str);
        registrar("/cuadrado", str -> str + " ^2= " + cuadrado("8")); // registrar numero al cuadrado key: /cuadrado servicio: resultado
        registrar("/cos", new Servicio1Param() {
            @Override
            public String handle(String s) {
                return String.valueOf(Math.cos(Double.parseDouble(s)));
            }
        });

        registrar("/sin", str -> {
            Double val = Double.parseDouble(str.split("val=")[1]);
            return String.valueOf(Math.sin(val));
        });

        buscar("/hello").handle("");

        System.out.println(buscar("/hello").handle("Ricardo"));
        System.out.println(buscar("/cuadrado").handle("8"));
        System.out.println("El Coseno de 0.77 rad es: " + buscar("/cos").handle("0.77"));
        System.out.println("El Seno de 0.55 rad es: " + buscar("/sin").handle("/sin?val=0.55"));
    }

    /**
     * Registra una key y un valor para el hash de servicios
     * @param url
     * @param servicio
     */
    public static void registrar(String url, Servicio1Param servicio){
        servicios.put(url,servicio);
    }

    /**
     * Busca el servicio de la url dada
     * @param url
     * @return
     */
    public static Servicio1Param buscar(String url){
        return servicios.get(url);
    }

    public static String cuadrado(String n){
        int num = Integer.parseInt(n);
        return String.valueOf(num*num);
    }

}
