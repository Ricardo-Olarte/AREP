package edu.escuelaing.arep;

@Component
public class HelloPost {

    @GetMapping("/hello")
    public static String getHello(String param){
        return "Null";
    }
}

