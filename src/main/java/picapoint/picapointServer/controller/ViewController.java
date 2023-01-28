package picapoint.picapointServer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import picapoint.picapointServer.service.EmpresaService;

@Controller
@RequestMapping("/")
public class ViewController {
    private final EmpresaService empresaService;

    public ViewController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public String login() {
        return "index";
    }

    @GetMapping("/registro")
    public void registro() {

    }

}
