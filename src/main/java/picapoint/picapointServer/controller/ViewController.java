package picapoint.picapointServer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import picapoint.picapointServer.entities.Login;
import picapoint.picapointServer.service.EmpresaService;

@Controller
@RequestMapping("/")
public class ViewController {
    private final EmpresaService empresaService;

    public ViewController(EmpresaService empresaService) {
        this.empresaService = empresaService;
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
}
