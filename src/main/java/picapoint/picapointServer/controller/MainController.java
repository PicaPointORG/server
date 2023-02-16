package picapoint.picapointServer.controller;

import com.auth0.jwt.JWT;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import picapoint.picapointServer.entities.*;
import picapoint.picapointServer.service.DatabaseService;
import picapoint.picapointServer.util.AuthCookie;
import picapoint.picapointServer.util.CustomClaims;
import picapoint.picapointServer.util.JWTHandler;
import picapoint.picapointServer.util.Role;

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
    public void login(HttpServletResponse response, @ModelAttribute("login") Login login)
            throws NoSuchAlgorithmException, IOException {
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

    @PostMapping("/registro/empresa")
    public void registro(@CookieValue(AuthCookie.NAME) String token, Usuario usuario, HttpServletResponse response) throws IOException {
        String role = JWT.decode(token).getClaim(CustomClaims.USER_ROLE.getValue()).asString();
        if (!role.equals(Role.ADMIN.getValue())) {
            response.setStatus(403); // Forbidden
            return;
        }
        databaseService.createUser(usuario);
        response.sendRedirect("/login");
    }

    @PostMapping("/registro/maquina")
    public void register(@CookieValue(AuthCookie.NAME) String token, Maquina maquina, HttpServletResponse response) throws IOException {
        String role = JWT.decode(token).getClaim(CustomClaims.USER_ROLE.getValue()).asString();
        if (!role.equals(Role.ADMIN.getValue())) {
            response.setStatus(403); // Forbidden
            return;
        }
        Empresa empresa = databaseService.getEmpresa(maquina.getEmpresa().getCif());
        maquina.setEmpresa(empresa);
        databaseService.createMaquina(maquina);
        response.sendRedirect("/maquinas");
    }
    @PostMapping("/stock/{idMaquina}/add")
    public void createStock(@PathVariable("idMaquina") Long idMaquina,
                         @RequestBody Long idProducto,
                         @RequestBody Integer cantidad,
                         @CookieValue(AuthCookie.NAME) String token,
                         HttpServletResponse response) {
        String cif = JWT.decode(token).getClaim(CustomClaims.USER_CIF.getValue()).asString();
        Maquina maquina = databaseService.getMaquina(idMaquina);
        Producto producto = databaseService.getProducto(idProducto);
        if (!maquina.getEmpresa().getCif().equals(cif) ||
                !producto.getEmpresa().getCif().equals(cif)) {
            response.setStatus(403); // Forbidden
            return;
        }
        MaquinaHasProducto newMph = new MaquinaHasProducto();
        newMph.setMaquina(maquina);
        newMph.setProducto(producto);
        newMph.setStock(cantidad);
        maquina.getMaquinaHasProductos().add(newMph);
        databaseService.updateStock(newMph);
        response.setStatus(200); // OK
    }

    @PostMapping("/producto/add")
    public void createProducto(@RequestBody Producto producto,
                               @CookieValue(AuthCookie.NAME) String token,
                               HttpServletResponse response) {
        String cif = JWT.decode(token).getClaim(CustomClaims.USER_CIF.getValue()).asString();
        if (!producto.getEmpresa().getCif().equals(cif)) {
            response.setStatus(403); // Forbidden
            return;
        }
        databaseService.createProducto(producto);
        response.setStatus(200); // OK
    }

    // --------------------------------- PUT ---------------------------------
    @PutMapping("/producto/update")
    public void updateProducto(@RequestBody Producto producto,
                               @CookieValue(AuthCookie.NAME) String token,
                               HttpServletResponse response) {
        String cif = JWT.decode(token).getClaim(CustomClaims.USER_CIF.getValue()).asString();
        if (!producto.getEmpresa().getCif().equals(cif)) {
            response.setStatus(403); // Forbidden
            return;
        }
        databaseService.updateProducto(producto);
        response.setStatus(200); // OK
    }
    @PutMapping("/maquina/update")
    public void updateMaquina(@RequestBody Maquina maquina,
                              @CookieValue(AuthCookie.NAME) String token,
                              HttpServletResponse response) {
        String cif = JWT.decode(token).getClaim(CustomClaims.USER_CIF.getValue()).asString();
        if (!maquina.getEmpresa().getCif().equals(cif)) {
            response.setStatus(403); // Forbidden
            return;
        }
        databaseService.updateMaquina(maquina);
        response.setStatus(200); // OK
    }
    @PutMapping("/stock/{idMaquina}/update")
    public void updateStock(@PathVariable("idMaquina") Long idMaquina,
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
        response.setStatus(200); // OK
    }

    // --------------------------------- DELETE ---------------------------------
    @DeleteMapping("/producto/{id}/delete")
    public void deleteProducto(@PathVariable("id") Long id,
                               @CookieValue(AuthCookie.NAME) String token,
                               HttpServletResponse response) {
        String cif = JWT.decode(token).getClaim(CustomClaims.USER_CIF.getValue()).asString();
        Producto producto = databaseService.getProducto(id);
        if (!producto.getEmpresa().getCif().equals(cif)) {
            response.setStatus(403); // Forbidden
            return;
        }
        databaseService.deleteProducto(producto.getId());
        response.setStatus(200); // OK
    }

    @DeleteMapping("/stock/{idMaquina}/delete")
    public void deleteStock(@PathVariable("idMaquina") Long idMaquina,
                            @RequestBody Long idProducto,
                            @CookieValue(AuthCookie.NAME) String token,
                            HttpServletResponse response) {
        String cif =JWTHandler.getCifFromToken(token);
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
        mhp.setProducto(null);
        databaseService.updateStock(mhp);
        response.setStatus(200); // OK
    }
}
