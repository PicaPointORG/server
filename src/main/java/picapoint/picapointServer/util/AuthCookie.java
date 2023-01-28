package picapoint.picapointServer.util;

import jakarta.servlet.http.Cookie;

public class AuthCookie extends Cookie {
    public static final String NAME = "Authorization";
    /**
     * Constructs a cookie with a specified NAME and token.
     * <p>
     * The cookie's NAME cannot be changed after creation.
     * <p>
     * The token can be anything the server chooses to send. Its token is
     * probably of interest only to the server. The cookie's token can be
     * changed after creation with the <code>setValue</code> method.
     *
     * @param token a value of the Jwt token
     * @throws IllegalArgumentException if the cookie NAME contains illegal characters
     * @see #setValue
     */
    public AuthCookie(String token) {
        super(NAME, token);
    }
}
