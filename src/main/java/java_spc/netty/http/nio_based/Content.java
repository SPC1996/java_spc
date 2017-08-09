package java_spc.netty.http.nio_based;

/**
 * An Sendable interface extension that adds additional
 * methods for additional information, such as Files or
 * Strings.
 */
public interface Content extends Sendable {
    String type();

    long length();
}
