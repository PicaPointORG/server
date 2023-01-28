package picapoint.picapointServer.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import picapoint.picapointServer.entities.Empresa;
import picapoint.picapointServer.entities.Login;
import picapoint.picapointServer.service.EmpresaService;
import picapoint.picapointServer.util.AuthCookie;
import picapoint.picapointServer.util.JWTHandler;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/")
public class MainController {
    EmpresaService empresaService;

    public MainController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/login")
    public void login(HttpServletResponse response, @ModelAttribute("login") Login login) throws NoSuchAlgorithmException, IOException {
        String token = JWTHandler.createToken(login.getUsername(), "admin");
        AuthCookie cookie = new AuthCookie(token);
        cookie.setPath("/");
        response.addCookie(cookie);
        System.out.println("Token created");
        response.sendRedirect("/");
    }

    @PostMapping("/empresas")
    public List<Empresa> getEmpresas(){
        return empresaService.getAll();
    }
}
