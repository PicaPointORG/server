package picapoint.picapointServer.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import picapoint.picapointServer.entities.Login;
import picapoint.picapointServer.entities.Maquina;
import picapoint.picapointServer.entities.Producto;
import picapoint.picapointServer.entities.Usuario;
import picapoint.picapointServer.service.DatabaseService;
import picapoint.picapointServer.util.AuthCookie;
import picapoint.picapointServer.util.CustomClaims;
import picapoint.picapointServer.util.JWTHandler;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/")
public class MainController {
    DatabaseService databaseService;

    public MainController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @PostMapping("/login")
    public void login(HttpServletResponse response, @ModelAttribute("login") Login login) throws NoSuchAlgorithmException, IOException {
        Usuario usuario = databaseService.getUsuario(login.getUsername(), login.getPassword());
        if (usuario == null) {
            response.sendRedirect("/login");
            return;
        }
        String token = JWTHandler.createToken(usuario.getUsername(), usuario.getRol(), usuario.getEmpresa().getCif());
        AuthCookie cookie = new AuthCookie(token);
        cookie.setPath("/");
        response.addCookie(cookie);
        System.out.println("Token created");
        response.sendRedirect("/");
    }
}
