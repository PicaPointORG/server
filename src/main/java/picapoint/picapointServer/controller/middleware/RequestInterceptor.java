package picapoint.picapointServer.controller.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Aqui se implementan los middlewares
        return Middleware.checkAuthenticationCookie(request, response);
    }
}

@Component
class RequestInterceptorAppConfig extends WebMvcConfigurationSupport {
    private final RequestInterceptor defaultInterceptor;

    public RequestInterceptorAppConfig(RequestInterceptor defaultInterceptor) {
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
