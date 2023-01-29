package picapoint.picapointServer.controller;

import com.auth0.jwt.JWT;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import picapoint.picapointServer.entities.Empresa;
import picapoint.picapointServer.entities.Login;
import picapoint.picapointServer.entities.Maquina;
import picapoint.picapointServer.entities.Producto;
import picapoint.picapointServer.service.DatabaseService;
import picapoint.picapointServer.util.AuthCookie;
import picapoint.picapointServer.util.CustomClaims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ViewController {
    private final DatabaseService databaseService;

    public ViewController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "register";
    }

    @GetMapping("/maquinas")
    public String maquinas(@CookieValue(AuthCookie.NAME) String token, Model model) {
        String cif = JWT.decode(token).getClaim(CustomClaims.USER_CIF.getValue()).asString();
        List<Maquina> maquinas = databaseService.getMaquinas(cif);
        model.addAttribute("maquinas", maquinas);
        return "maquinas";
    }

    @GetMapping("/maquinas/{id}")
    public String maquina(HttpServletResponse response, @PathVariable("id") long id, Model model,
                          @CookieValue(AuthCookie.NAME) String token) {
        String cif = JWT.decode(token).getClaim(CustomClaims.USER_CIF.getValue()).asString();
        Maquina maquina = databaseService.getMaquina(id);
        if (maquina == null) {
            response.setStatus(404); // Not found
            return null;
        } else if (!maquina.getEmpresa().getCif().equals(cif)) {
            response.setStatus(403); // Forbidden
            return null;
        }
        List<Maquina> maquinas = new ArrayList<>();
        maquinas.add(maquina);
        model.addAttribute("maquinas", maquinas);
        return "maquinas";
    }

    @GetMapping("/productos")
    public String productos(@CookieValue(AuthCookie.NAME) String token, Model model) {
        String cif = JWT.decode(token).getClaim(CustomClaims.USER_CIF.getValue()).asString();
        List<Producto> productos = databaseService.getProductos(cif);
        model.addAttribute("productos", productos);
        Map<Long, Integer> totalStock = new HashMap<>();
        for (Producto producto : productos) {
            Integer productStock = producto.getMaquinaHasProductos().stream().reduce(
                    0,
                    (a, b) -> a + b.getStock(),
                    Integer::sum
            );
            totalStock.put(producto.getId(), productStock);
        }
        model.addAttribute("stock", totalStock);
        return "productos";
    }
}
