package edu.escuelaing.arep;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class Reflexion {

    private static Member[] mbrs;
    private static Object String;
    private static Object s;

    public static void main(String[] args) {
        Class c = "hola".getClass();
        System.out.println("La clase es: " + c);

        byte[] bytes = new byte[1024];
        System.out.println("La clase es: " + c);

        c = bytes.getClass();
        System.out.println("La clase es: " + c);

        c = boolean.class;
        System.out.println("La clase es: " + c);

        try {
            c = Class.forName("edu.escuelaing.arep.example");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("La clase es: " + c);


        c = Double.TYPE;
        System.out.println("La clase es: " + c);

        c = javax.swing.JButton.class.getClass();
        System.out.println("La clase es: " + c);

        printMembers(c.getFields(), "Fields");
    }

    private static void printMembers(Member[] mbrs, String s){
        System.out.format("%s:%n", s);
        for (Member mbr : mbrs) {
            if (mbr instanceof Field)
                System.out.format(" %s%n", ((Field) mbr).toGenericString());
            else if (mbr instanceof Constructor)
                System.out.format(" %s%n", ((Constructor) mbr).toGenericString());
            else if (mbr instanceof Method)
                System.out.format(" %s%n", ((Method) mbr).toGenericString());
        }
        if (mbrs.length == 0){
            System.out.format(" -- No %s --%n", s);
        }
        System.out.format("%n");
    }
}
