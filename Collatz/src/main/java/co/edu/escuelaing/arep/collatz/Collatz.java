package co.edu.escuelaing.arep.collatz;

import static spark.Spark.*;

/**
 * @author jose.olarte
 */
public class Collatz{

    public static void main(String[] args) {
        port(getPort());
        get("collatzsequence", (request,response)->{
                 String out = collatzDo(request.queryParams("value"));
                 return "{\n" +
                         "\"operation\":" + "\"collatzsequence\","+
                         "\"input\":" + request.queryParams("value") + "," +
                         "\"output\":" + "\"" + out + "\"" +
                         "}";
        });
    }

    /**
     * Singleton principle
     * @return integer
     */
    private static int getPort(){
        if(System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    /**
     * Do the collatz sequence
     * @param string
     * @return
     */
    private static String collatzDo(String string){

        try{
            int n = Integer.parseInt(string);
            String secuencia = "" + n;
            while(n>1){
                if(n%2 == 0){
                    n = n/2;
                }else{
                    n = (3*n)+1;
                }
                secuencia = secuencia + "->" + n;
            }
            return secuencia;
        }catch (Exception ex){
            return "Not valid input";
        }
    }

}
