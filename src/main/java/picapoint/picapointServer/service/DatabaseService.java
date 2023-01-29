package picapoint.picapointServer.service;

import org.springframework.stereotype.Service;
import picapoint.picapointServer.entities.Maquina;
import picapoint.picapointServer.entities.Producto;
import picapoint.picapointServer.entities.Usuario;
import picapoint.picapointServer.repository.EmpresaRepository;
import picapoint.picapointServer.repository.MaquinaRepository;
import picapoint.picapointServer.repository.ProductoRepository;
import picapoint.picapointServer.repository.UsuarioRepository;

import java.util.List;

@Service
public class DatabaseService {
    private final EmpresaRepository empresaRepository;
    private final ProductoRepository productoRepository;
    private final MaquinaRepository maquinaRepository;
    private final UsuarioRepository usuarioRepository;

    public DatabaseService(EmpresaRepository empresaRepository, ProductoRepository productoRepository, MaquinaRepository maquinaRepository, UsuarioRepository usuarioRepository) {
        this.empresaRepository = empresaRepository;
        this.productoRepository = productoRepository;
        this.maquinaRepository = maquinaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario getUsuario(String username, String password) {
        return usuarioRepository.findByUsernameAndPassword(username, password);
    }

    public List<Maquina> getMaquinas(String cif) {
        return maquinaRepository.findByEmpresaCif(cif);
    }

    public Maquina getMaquina(Long id) {
        return maquinaRepository.findById(id).orElse(null);
    }

    public List<Producto> getProductos(String cif) {
        return productoRepository.findByEmpresaCif(cif);
    }
}
