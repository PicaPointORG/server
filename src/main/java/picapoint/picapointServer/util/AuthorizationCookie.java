package picapoint.picapointServer.util;

import jakarta.servlet.http.Cookie;

public class AuthorizationCookie extends Cookie {
    public static final String NAME = "Authorization";
    /**
     * Constructs a cookie with a specified NAME and value.
     * <p>
     * The cookie's NAME cannot be changed after creation.
     * <p>
     * The value can be anything the server chooses to send. Its value is
     * probably of interest only to the server. The cookie's value can be
     * changed after creation with the <code>setValue</code> method.
     *
     * @param value a <code>String</code> Token value of the cookie
     * @throws IllegalArgumentException if the cookie NAME contains illegal characters
     * @see #setValue
     */
    public AuthorizationCookie(String value) {
        super(NAME, value);
    }
}
