package picapoint.picapointServer.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;
import picapoint.picapointServer.entities.Empresa;
import picapoint.picapointServer.service.EmpresaService;

import java.util.List;

@RestController
@RequestMapping("/")
public class MainController{
    EmpresaService empresaService;

    public MainController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/empresas")
    public List<Empresa> getEmpresas(HttpServletResponse response) {
        // create a cookie
        Cookie cookie = new Cookie("username", "Jovan");
        ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, "").build();
        //add cookie to response
        response.addCookie(cookie);
        return empresaService.getAll();
    }
}
