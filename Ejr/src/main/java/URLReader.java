import java.io.*;
import java.net.*;

public class URLReader {

    public static void main(String[] args) throws Exception{
        try {
            URL myURL = new URL("https://campusvirtual.escuelaing.edu.co:7896/moodle/course/view.php?id=892");
            System.out.println("Protocol:  "+ myURL.getProtocol());
            System.out.println("Authority:  "+ myURL.getAuthority());
            System.out.println("Host:  "+ myURL.getHost());
            System.out.println("Port:  "+ myURL.getPort());
            System.out.println("Path:  "+ myURL.getPath());
            System.out.println("Query:  "+ myURL.getQuery());
            System.out.println("File:  "+ myURL.getFile());
            System.out.println("Ref:  #"+ myURL.getRef());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
