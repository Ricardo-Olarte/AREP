package co.edu.escuelaing.arep.logservice;

import static spark.Spark.*;

public class LogService {

    public static void main(String[] args) {
        port(getPort());
        get("/logservice", (req, pesp) -> {
            String val = req.queryParams("value");
            return logMessage(val);
        });

    }

    private static int getPort(){
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568;
    }

    private static String logMessage(String string){
        return """
                    {
                    "m1": "message1"
                    "m2": "message2"
                    "m3": "message3"
                    }
               """;
    }
}
