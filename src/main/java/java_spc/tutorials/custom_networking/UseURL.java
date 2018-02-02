package java_spc.tutorials.custom_networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

/**
 * 观看java tutorials的custom networking模块时所写的代码
 */
public class UseURL {
    private static final Logger LOG = Logger.getLogger("UseURL");

    public static void main(String[] args) {
        createUrlByString();
        createUrlByAnother();
        createUrlByOtherConstructors();
        createUrlWithSpecialCharacters();
        parsingUrl();
        readDirectlyFromUrl();
        connectToUrl();
    }

    private static void createUrlByString() {
        try {
            URL url = new URL("https://www.baidu.com");
            System.out.printf("the protocol is %s, the host is %s, the port is %s%n",
                    url.getProtocol(),
                    url.getHost(),
                    url.getPort());
        } catch (MalformedURLException e) {
            LOG.severe("url transfer is error!");
        }
    }

    private static void createUrlByAnother() {
        try {
            URL base = new URL("https://www.baidu.com/");
            URL page = new URL(base, "page.html");
            URL text = new URL(base, "note.txt");
            System.out.printf("page url is %s, text url is %s%n", page, text);
        } catch (MalformedURLException e) {
            LOG.severe("url transfer is error!");
        }
    }

    private static void createUrlByOtherConstructors() {
        try {
            URL url = new URL("https", "www.baidu.com", 8080, "/error.html");
            System.out.println(url);
        } catch (MalformedURLException e) {
            LOG.severe("url transfer is error!");
        }
    }

    private static void createUrlWithSpecialCharacters() {
        try {
            URI uri = new URI("https", "www.baidu.com", "/p a g e.html/", "");
            System.out.println(uri.toURL());
        } catch (URISyntaxException | MalformedURLException e) {
            LOG.severe("url transfer is error!");
        }
    }

    private static void parsingUrl() {
        try {
            URL url = new URL("http://www.example.com:80/books/index.html?name=net#DOWNLOADING");
            System.out.println("protocol = " + url.getProtocol());
            System.out.println("authority = " + url.getAuthority());
            System.out.println("host = " + url.getHost());
            System.out.println("port = " + url.getPort());
            System.out.println("path = " + url.getPath());
            System.out.println("query = " + url.getQuery());
            System.out.println("filename = " + url.getFile());
            System.out.println("ref = " + url.getRef());
        } catch (MalformedURLException e) {
            LOG.severe("url transfer is error!");
        }
    }

    private static void readDirectlyFromUrl() {
        try {
            URL url = new URL("https://www.baidu.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
        } catch (IOException e) {
            LOG.severe("url transfer is error!");
        }
    }

    private static void connectToUrl() {
        try {
            URL url = new URL("https://www.baidu.com");
            URLConnection connection = url.openConnection();
            System.out.println(connection.getHeaderFields());
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
        } catch (IOException e) {
            LOG.severe("open url connection failed!");
        }
    }
}
