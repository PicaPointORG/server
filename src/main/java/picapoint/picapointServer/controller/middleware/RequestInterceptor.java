package picapoint.picapointServer.controller.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Clase RequestInterceptor
 * <p>
 * Esta clase es una implementación de HandlerInterceptor y representa un interceptor de solicitudes
 * para ser utilizado en la aplicación.
 *
 * @Component - Anotación utilizada para indicar que la clase es un componente de la aplicación.
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {
    /**
     * Método preHandle
     * <p>
     * Este método es llamado antes de manejar una solicitud y se utiliza para implementar middlewares.
     *
     * @param request  - La solicitud HttpServlet recibida.
     * @param response - La respuesta HttpServlet a ser enviada.
     * @param handler  - El objeto encargado de manejar la solicitud.
     * @return un booleano que indica si se debe continuar con el manejo de la solicitud o no.
     * @throws Exception - En caso de ocurrir un error durante la ejecución del método.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Aquí se implementan los middlewares
        return Middleware.checkAuthenticationCookie(request, response);
    }
}

/**
 * Clase RequestInterceptorAppConfig
 * <p>
 * Esta clase es una implementación de WebMvcConfigurationSupport y representa la configuración
 * para el uso del interceptor RequestInterceptor en la aplicación.
 *
 */
@Component
class RequestInterceptorAppConfig extends WebMvcConfigurationSupport {
    private final RequestInterceptor defaultInterceptor;

    /**
     * Constructor
     * <p>
     * Este constructor inicializa el interceptor por defecto.
     *
     * @param defaultInterceptor - El interceptor por defecto.
     */
    public RequestInterceptorAppConfig(RequestInterceptor defaultInterceptor) {
        this.defaultInterceptor = defaultInterceptor;
    }

    /**
     * Método addResourceHandlers
     * <p>
     * Este método se utiliza para agregar manejadores de recursos en la aplicación.
     *
     * @param registry - El registro de manejadores de recursos.
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
    }

    /**
     * Método addInterceptors
     * <p>
     * Este método se utiliza para agregar interceptores en la aplicación.
     *
     * @param registry - El registro de interceptores.
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(defaultInterceptor)
                .excludePathPatterns("/css/**", "/source/**", "/error", "/favicon.ico", "/login", "/registro", "/iot");
    }
}
