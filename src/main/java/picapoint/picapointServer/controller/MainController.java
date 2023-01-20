package picapoint.picapointServer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import picapoint.picapointServer.entities.Empresa;
import picapoint.picapointServer.service.EmpresaService;

import java.util.List;

@RestController
@RequestMapping("/")
public class MainController {
    EmpresaService empresaService;
    public MainController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public List<Empresa> getAllEmpresas() {
        return empresaService.getAll();
    }
}
