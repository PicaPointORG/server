package picapoint.picapointServer.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import picapoint.picapointServer.entities.Empresa;
import picapoint.picapointServer.service.EmpresaService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class MainController {
    EmpresaService empresaService;

    public MainController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/login")
    public void login() {
    }

    @GetMapping("/registro")
    public void registro() {

    }

    @GetMapping("/empresas")
    public List<Empresa> getEmpresas(HttpServletResponse response) {
        // create a cookie
        Cookie cookie = new Cookie("username", "Jovan");

        //add cookie to response
        response.addCookie(cookie);
        return empresaService.getAll();
    }
}
