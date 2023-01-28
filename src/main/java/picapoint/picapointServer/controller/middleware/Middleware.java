package picapoint.picapointServer.controller.middleware;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import picapoint.picapointServer.util.AuthCookie;
import picapoint.picapointServer.util.CustomClaims;
import picapoint.picapointServer.util.JWTHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class Middleware {
    /**
     * Comprueba si el usuario tiene un cookie de autenticación válido
     * @return verdadero si tiene un cookie de autenticación válido
     * @throws IOException si la redirección falla
     */
    public static boolean checkAuthenticationCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]);
        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals(AuthCookie.NAME))
                .findFirst().orElse(null);
        if (cookie == null) {
            System.err.println("No token");
            response.sendRedirect("/login");
            return false;
        }
        DecodedJWT token = JWTHandler.verifyToken(cookie.getValue());
        if (token == null) {
            System.err.println("Invalid token");
            response.sendRedirect("/login");
            return false;
        }
        String username = token.getClaim(CustomClaims.USER_NAME.getValue()).asString();
        String role = token.getClaim(CustomClaims.USER_ROLE.getValue()).asString();
        String path = request.getRequestURI();
        System.out.println("User: " + username + " -- Role: " + role + " -- Path: " + path);
        return true;
    }
}
