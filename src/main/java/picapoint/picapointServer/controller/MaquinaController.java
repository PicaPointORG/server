package picapoint.picapointServer.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import picapoint.picapointServer.service.MaquinaService;

import java.util.Map;

@RestController
@RequestMapping("/maquina")
public class MaquinaController {
    MaquinaService maquinaService;

    public MaquinaController(MaquinaService maquinaService) {
        this.maquinaService = maquinaService;
    }

    @PostMapping("/update")
    public void updateMaquinaStock(@RequestParam Map<String, String> request) {
        String mac = request.get("mac");
        String idProducto = request.get("idProducto");
        maquinaService.updateMaquinaStock(mac, Long.parseLong(idProducto));
    }
}
