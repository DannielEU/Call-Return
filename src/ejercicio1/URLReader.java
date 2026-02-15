package ejercicio1;
import java.io.*;
import java.net.*;
import java.net.URI;

public class URLReader {
    public static void main(String[] args) throws Exception {
        URI urls = new URI("http://www.google.com/");
        System.out.println(urls.getAuthority());
        System.out.println(urls.getHost());
        System.out.println(urls.getPort());
        System.out.println(urls.getPath());
        System.out.println(urls.getQuery());
        System.out.println(urls.toURL().getFile());
        System.out.println(urls.toURL().getProtocol());
        System.out.println(urls.toURL().getRef());
    }
}
