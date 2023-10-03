package co.edu.escuelaing.arep;

import static spark.Spark.*;

public class HelloWorld {
    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/public");
        secure("keystores/ecikeypair.p12", "ecistore", null, null);
        get("/hello", (req, res) -> "Hello World");
    }

    private static int getPort(){
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }
}
