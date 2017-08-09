package java_spc.netty.http.nio_based;

import java_spc.util.Resource;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * A simple example to illustrate using a URL to access a resource
 * and then store the result to a File.
 */
public class URLDumper {
    private static final String RESOURCE_PATH_SOURCE = Resource.pathToSource("root\\");
    private static final String RESOURCE_PATH_RUN = Resource.path("root\\");

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println(URLDumper.class.getResource("/").getPath());
            System.out.println("Usage: URLDumper <URL> <file>");
            System.exit(1);
        }
        String location = args[0];
        String file = RESOURCE_PATH_SOURCE + args[1];

        URL url = new URL(location);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] bytes = new byte[4096];
        InputStream is = url.openStream();

        int read;
        while ((read = is.read(bytes)) != -1) {
            fos.write(bytes, 0, read);
        }
        is.close();
        fos.close();

        System.out.println("Get message from " + location + ", save message in " + file + ".");
    }
}
