package picapoint.picapointServer.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import picapoint.picapointServer.util.CustomClaims;
import picapoint.picapointServer.util.JwtTokenHandler;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class DefaultInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Intercepted: " + request.getRequestURI());
        if (request.getRequestURI().equals("/login")) {
            String token = createToken("admin", "admin");
            Cookie cookie = new Cookie("Authorization", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return true;
        }

        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]);
        String token = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName()
                        .equals("Authorization"))
                .findFirst().orElse(new Cookie("empty", null)).getValue();
        if (token == null) {
            System.out.println("No token");
            response.sendRedirect("/login");
            return false;
        }
        DecodedJWT validToken = verifyToken(token);
        if (validToken == null) {
            System.out.println("Invalid token");
            response.sendRedirect("/login");
            return false;
        }
        System.out.println("Valid token");
        System.out.println(token);
        return true;
    }

    private DecodedJWT verifyToken(String token) {
        DecodedJWT jwt;
        try {
            jwt = JWT.require(JwtTokenHandler.getAlgorithm()).build().verify(token);
        } catch (Exception e) {
            return null;
        }
        return jwt;
    }

    private String createToken(String username, String role) throws NoSuchAlgorithmException {
        return JWT.create().withClaim(CustomClaims.USER_NAME.getClaim(), username)
                .withClaim(CustomClaims.USER_ROLE.getClaim(), role)
                .withIssuer("auth0")
                .sign(JwtTokenHandler.getAlgorithm());
    }
}

@Component
class ProductServiceInterceptorAppConfig extends WebMvcConfigurationSupport {
    private final DefaultInterceptor defaultInterceptor;

    public ProductServiceInterceptorAppConfig(DefaultInterceptor defaultInterceptor) {
        this.defaultInterceptor = defaultInterceptor;
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(defaultInterceptor)
                .excludePathPatterns("/css/**", "/source/**", "/error", "/favicon.ico");
    }
}
