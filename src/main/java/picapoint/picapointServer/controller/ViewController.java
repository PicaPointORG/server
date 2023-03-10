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

    @GetMapping("/admin")
    public String index_admin() {
        return "index_admin";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @GetMapping("/registro/empresa")
    public String registroUsuario(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "registro_empresa";
    }

    @GetMapping("/registro/maquina")
    public String registroMaquina(Model model) {
        List<Empresa> empresas = databaseService.getEmpresas();
        Maquina maquina = new Maquina();
        maquina.setEmpresa(new Empresa());
        model.addAttribute("maquina", maquina );
        model.addAttribute("empresas", empresas);
        return "registro_maquina";
    }

    @GetMapping("/empresas")
    public String empresa(@CookieValue(AuthCookie.NAME) String token, Model model) {
        List<Empresa> empresa = databaseService.getEmpresas();
        model.addAttribute("empresas", empresa);
        return "listado_empresas";
    }

    @GetMapping("/maquinas")
    public String maquinas(@CookieValue(AuthCookie.NAME) String token, Model model) {
        /*List<Empresa> empresas = databaseService.getEmpresas();
        List<Maquina> maquinas_empresa;
        ArrayList<Maquina> maquinas = new ArrayList<>();
        String cif;
        for(int i=0; i<empresas.size();i++){
            cif = empresas.get(i).getCif();
            maquinas_empresa = databaseService.getMaquinas(cif);
            for(int j=0; j<maquinas_empresa.size();i++){
                maquinas.add(maquinas_empresa.get(j));
            }
        }*/
        String cif = JWT.decode(token).getClaim(CustomClaims.USER_CIF.getValue()).asString();
        List<Maquina> maquinas = databaseService.getMaquinas(cif);

        model.addAttribute("maquinas", maquinas);
        return "maquinas";
    }
    @GetMapping("/listado_maquinas")
    public String listado_maquinas(@CookieValue(AuthCookie.NAME) String token, Model model) {

        List<Maquina> maquinas = databaseService.getAllMaquinas();

        model.addAttribute("maquinas", maquinas);
        return "listado_maquinas";
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

        model.addAttribute("maquina", maquina);
        return "maquinaVista";
    }

    @GetMapping("/productos")
    public String productos(@CookieValue(AuthCookie.NAME) String token, Model model) {
        String cif = JWT.decode(token).getClaim(CustomClaims.USER_CIF.getValue()).asString();
        List<Producto> productos = databaseService.getProductos(cif);
        Map<Producto, Integer> totalStockDeCadaProducto = new HashMap<>();
        for (Producto producto : productos) {
            Integer productStock = producto.getMaquinaHasProductos().stream().reduce(
                    0,
                    (a, b) -> a + b.getStock(),
                    Integer::sum
            );
            totalStockDeCadaProducto.put(producto, productStock);
        }
        model.addAttribute("productos", totalStockDeCadaProducto);
        return "productos";
    }

    @GetMapping("/productos/{id}")
    public String producto(HttpServletResponse response, @PathVariable("id") long id, Model model,
                           @CookieValue(AuthCookie.NAME) String token) {
        String cif = JWT.decode(token).getClaim(CustomClaims.USER_CIF.getValue()).asString();
        Producto producto = databaseService.getProducto(id);
        if (producto == null) {
            response.setStatus(404); // Not found
            return null;
        } else if (!producto.getEmpresa().getCif().equals(cif)) {
            response.setStatus(403); // Forbidden
            return null;
        }
        Map<Producto, Integer> totalStockDeCadaProducto = new HashMap<>();
        Integer productStock = producto.getMaquinaHasProductos().stream().reduce(
                0,
                (a, b) -> a + b.getStock(),
                Integer::sum
        );
        totalStockDeCadaProducto.put(producto, productStock);
        model.addAttribute("producto", totalStockDeCadaProducto);
        return null;
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
