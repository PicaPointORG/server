package picapoint.picapointServer.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import picapoint.picapointServer.util.AuthCookie;
import picapoint.picapointServer.util.CustomClaims;
import picapoint.picapointServer.util.JWTHandler;

import java.util.Arrays;
import java.util.Optional;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]);
        String token = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(AuthCookie.NAME))
                .findFirst().orElse(new AuthCookie(null)).getValue();
        if (token == null) {
            System.err.println("No token");
            response.sendRedirect("/login");
            return false;
        }
        DecodedJWT verifiedJwt = JWTHandler.verifyToken(token);
        if (verifiedJwt == null) {
            System.err.println("Invalid token");
            response.sendRedirect("/login");
            return false;
        }
        String username = verifiedJwt.getClaim(CustomClaims.USER_NAME.getValue()).asString();
        String role = verifiedJwt.getClaim(CustomClaims.USER_ROLE.getValue()).asString();
        String path = request.getRequestURI();
        System.out.println("User: " + username + " -- Role: " + role + " -- Path: " + path);
        return true;
    }
}

@Component
class ProductServiceInterceptorAppConfig extends WebMvcConfigurationSupport {
    private final RequestInterceptor defaultInterceptor;

    public ProductServiceInterceptorAppConfig(RequestInterceptor defaultInterceptor) {
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
                .excludePathPatterns("/css/**", "/source/**", "/error", "/favicon.ico", "/login", "/registro");
    }
}
