package edu.escuelaing.app;

import javax.print.URIException;
import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpServer {

    /*
    Patron SINGLETON
     */
    private static HttpServer _instance = new HttpServer();
    private HttpServer(){}
    private static boolean started = false;
    private static Map<String, Servicio1Param> servicios = new HashMap<String, Servicio1Param>();

    /*
    SOLO UNA INSTANCIA
     */
    public static HttpServer getInstance(){
        return _instance;
    }

    public static void get(String url, Servicio1Param servicio){
        servicios.put(url, servicio);
    }


    public void start(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while(running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            String uriString = "";
            String path = "";

            boolean firstLine = true;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if(firstLine){
                    firstLine = false;
                    uriString = inputLine.split(" ")[1];
                    path = inputLine.split(" ")[1];
                }
                if (!in.ready()) {
                    break;
                }
            }

            String resource = path.substring(0, path.indexOf("?"));
            Servicio1Param servicio = buscar(resource);
            URI requestduri = null;
            try {
                requestduri = new URI(path);
                servicio = buscar(requestduri.getPath());
            }catch (URISyntaxException e){
                Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null,e);
            }


            System.out.println("URI: " + uriString);

            if(servicio != null){
                getServicio(servicio, requestduri, out);
                outputLine = getHello(uriString);
            }else{
                getFile(path,out);
                outputLine = indexResponse();
            }

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    private Servicio1Param buscar(String resource){
        return servicios.get(resource);
    }

    private  void getServicio(String url, URI requesturi, OutputStream out){
        String header = "";
        PrintWriter printWriter = new PrintWriter(out, true);
        String contentTYoe = "text/html";
        printWriter.println(header);
        printWriter.println(requesturi.getQuery());
    }

    private static void getFile(String path, OutputStream out){
        URI requestduri = null;
        try{
            requestduri = new URI("target/classes/public" + path);
        }catch (URISyntaxException e){

        }
    }
    public static String getHello(String uri){
        String name = uri.replace("/hello?name=", "");
        return "{ \"msg\": \"Hello " + name + "\"}";
    }

    public static  String indexResponse(){
        String response = "HTTP/1.1 200 OK\r\n"
                + "Content-type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Form Example</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <h1>Form with GET</h1>\n"
                + "        <form action=\"/hello\">\n"
                + "            <label for=\"name\">Name:</label><br>\n"
                + "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n"
                + "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n"
                + "        </form> \n"
                + "        <div id=\"getrespmsg\"></div>\n"
                + "\n"
                + "        <script>\n"
                + "            function loadGetMsg() {\n"
                + "                let nameVar = document.getElementById(\"name\").value;\n"
                + "                const xhttp = new XMLHttpRequest();\n"
                + "                xhttp.onload = function() {\n"
                + "                    document.getElementById(\"getrespmsg\").innerHTML =\n"
                + "                    this.responseText;\n"
                + "                }\n" + "                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n"
                + "                xhttp.send();\n"
                + "            }\n"
                + "        </script>\n"
                + "\n"
                + "        <h1>Form with POST</h1>\n"
                + "        <form action=\"/hellopost\">\n"
                + "            <label for=\"postname\">Name:</label><br>\n"
                + "            <input type=\"text\" id=\"postname\" name=\"name\" value=\"John\"><br><br>\n"
                + "            <input type=\"button\" value=\"Submit\" onclick=\"loadPostMsg(postname)\">\n"
                + "        </form>\n"
                + "        \n"
                + "        <div id=\"postrespmsg\"></div>\n"
                + "        \n" + "        <script>\n"
                + "            function loadPostMsg(name){\n"
                + "                let url = \"/hellopost?name=\" + name.value;\n"
                + "\n"
                + "                fetch (url, {method: 'POST'})\n"
                + "                    .then(x => x.text())\n"
                + "                    .then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n"
                + "            }\n"
                + "        </script>\n"
                + "    </body>\n"
                + "</html>";
        return response;
    }
}