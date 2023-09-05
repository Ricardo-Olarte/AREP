package edu.escuelaing.arep;

import edu.escuelaing.arep.Test;

public class TestClass {

    @Test
    public static void a() throws Exception{
        throw(new Exception("exception example"));
    }

    public static void b() throws IllegalAccessError{
        throw(new IllegalAccessError("exception example"));
    }

    public static void c() throws Exception {
        a();
    }

    public static void d() {

    }

    public static void e() {

    }
}
