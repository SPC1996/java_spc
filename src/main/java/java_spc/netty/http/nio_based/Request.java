package java_spc.netty.http.nio_based;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An encapsulation of the request received.
 * <p>
 * The static method parse() is responsible for creating this
 * object.
 */
public class Request {
    /**
     * A helper class for parsing HTTP command actions.
     */
    static class Action {
        private String name;
        static Action GET = new Action("GET");
        static Action PUT = new Action("PUT");
        static Action POST = new Action("POST");
        static Action HEAD = new Action("HEAD");

        private Action(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }

        static Action parse(String s) {
            if (s.equals("GET"))
                return GET;
            if (s.equals("PUT"))
                return PUT;
            if (s.equals("POST"))
                return POST;
            if (s.equals("HEAD"))
                return HEAD;
            throw new IllegalArgumentException(s);
        }

    }

    private Action action;
    private String version;
    private URI uri;

    Action action() {
        return action;
    }

    String version() {
        return version;
    }

    URI uri() {
        return uri;
    }

    private Request(Action action, String version, URI uri) {
        this.action = action;
        this.version = version;
        this.uri = uri;
    }

    @Override
    public String toString() {
        return action + " " + version + " " + uri;
    }

    static boolean isComplete(ByteBuffer bb) {
        int p = bb.position() - 4;

        return p >= 0 &&
                bb.get(p) == '\r' &&
                bb.get(p + 1) == '\n' &&
                bb.get(p + 2) == '\r' &&
                bb.get(p + 3) == '\n';
    }

    private static Charset ascii = Charset.forName("US-ASCII");

    /*
     * The excepted message format is first compiled into a pattern,
     * and is then compared against the inbound character buffer to
     * determine if there is a match. This conveniently tokenize
     * our request into usable pieces.
     *
     * This uses Matcher "expression capture groups" to tokenize
     * request like:
     *      GET /dir/file HTTP/1.1
     *      Host: hostname
     * into:
     *      group[1]="GET"
     *      group[2]="/dir/file"
     *      group[3]="1.1"
     *      group[4]="hostname"
     */
    private static Pattern requestPattern = Pattern.compile("" +
                    "\\A([A-Z]+) +([^ ]+) +HTTP/([0-9\\.]+)$" +
                    ".*^Host: ([^ ]+)$.*\r\n\r\n\\z",
            Pattern.MULTILINE | Pattern.DOTALL);

    static Request parse(ByteBuffer bb) throws MalformedRequestException {
        CharBuffer cb = ascii.decode(bb);
        Matcher m = requestPattern.matcher(cb);
        if (!m.matches()) {
            throw new MalformedRequestException();
        }
        Action action;
        try {
            action = Action.parse(m.group(1));
        } catch (IllegalArgumentException e) {
            throw new MalformedRequestException();
        }
        URI uri;
        try {
            uri = new URI("http://" + m.group(4) + m.group(2));
        } catch (URISyntaxException e) {
            throw new MalformedRequestException();
        }
        return new Request(action, m.group(3), uri);
    }
}
