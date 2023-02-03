package picapoint.picapointServer.controller;

import com.auth0.jwt.JWT;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import picapoint.picapointServer.entities.Login;
import picapoint.picapointServer.entities.Maquina;
import picapoint.picapointServer.entities.MaquinaHasProducto;
import picapoint.picapointServer.entities.Usuario;
import picapoint.picapointServer.service.DatabaseService;
import picapoint.picapointServer.util.AuthCookie;
import picapoint.picapointServer.util.CustomClaims;
import picapoint.picapointServer.util.JWTHandler;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/")
public class MainController {
    DatabaseService databaseService;

    public MainController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    // --------------------------------- POST ---------------------------------
    @PostMapping("/login")
    public boolean login(HttpServletResponse response, @ModelAttribute("login") Login login) throws NoSuchAlgorithmException, IOException {
        Usuario usuario = databaseService.getUsuario(login.getUsername(), login.getPassword());
        if (usuario == null) {
            response.sendRedirect("/login");
            return false;
        }
        String token = JWTHandler.createToken(usuario.getUsername(), usuario.getRol(), usuario.getEmpresa().getCif());
        AuthCookie cookie = new AuthCookie(token);
        cookie.setPath("/");
        response.addCookie(cookie);
        System.out.println("Token createds");
        response.sendRedirect("/");
        return true;
    }

    // --------------------------------- PUT ---------------------------------
    @PutMapping("/stock/{idMaquina}/add")
    public void addStock(@PathVariable("idMaquina") Long idMaquina,
                         @RequestBody Long idProducto,
                         @RequestBody Integer cantidad,
                         @CookieValue(AuthCookie.NAME) String token,
                         HttpServletResponse response) {
        String cif = JWT.decode(token).getClaim(CustomClaims.USER_CIF.getValue()).asString();
        Maquina maquina = databaseService.getMaquina(idMaquina);
        if (!maquina.getEmpresa().getCif().equals(cif)) {
            response.setStatus(403); // Forbidden
            return;
        }
        MaquinaHasProducto mhp = maquina.getMaquinaHasProductos().stream()
                .filter(m -> m.getProducto().getId().equals(idProducto))
                .findFirst().orElse(null);
        if (mhp == null) {
            response.setStatus(404); // Not found
            return;
        }
        mhp.setStock(mhp.getStock() + cantidad);
        databaseService.updateStock(mhp);
    }

    // --------------------------------- DELETE ---------------------------------
}
