package picapoint.picapointServer.service;

import org.springframework.stereotype.Service;
import picapoint.picapointServer.entities.*;
import picapoint.picapointServer.repository.*;

import java.util.List;

@Service
public class DatabaseService {
    private final EmpresaRepository empresaRepository;
    private final ProductoRepository productoRepository;
    private final MaquinaRepository maquinaRepository;
    private final UsuarioRepository usuarioRepository;
    private final StockRepository stockRepository;

    public DatabaseService(EmpresaRepository empresaRepository, ProductoRepository productoRepository, MaquinaRepository maquinaRepository, UsuarioRepository usuarioRepository, StockRepository stockRepository) {
        this.empresaRepository = empresaRepository;
        this.productoRepository = productoRepository;
        this.maquinaRepository = maquinaRepository;
        this.usuarioRepository = usuarioRepository;
        this.stockRepository = stockRepository;
    }

    /**
     * Obtiene un usuario dado su nombre de usuario y contraseña.
     *
     * @param username nombre de usuario
     * @param password contraseña
     * @return un objeto de tipo Usuario si existe, null en caso contrario
     */
    public Usuario getUsuario(String username, String password) {
        return usuarioRepository.findByUsernameAndPassword(username, password);
    }

    /**
     * Obtiene una lista de máquinas dados el CIF de una empresa.
     *
     * @param cif CIF de una empresa
     * @return una lista de objetos de tipo Maquina
     */
    public List<Maquina> getMaquinas(String cif) {
        return maquinaRepository.findByEmpresaCif(cif);
    }

    /**
     * Obtiene una máquina dado su identificador único.
     *
     * @param id identificador único de la máquina
     * @return un objeto de tipo Maquina si existe, null en caso contrario
     */
    public Maquina getMaquina(Long id) {
        return maquinaRepository.findById(id).orElse(null);
    }

    /**
     * Obtiene una lista de productos dados el CIF de una empresa.
     *
     * @param cif CIF de una empresa
     * @return una lista de objetos de tipo Producto
     */
    public List<Producto> getProductos(String cif) {
        return productoRepository.findByEmpresaCif(cif);
    }

    public Producto getProducto(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public List<Empresa> getEmpresas() {
        return empresaRepository.findAll();
    }

    public Empresa getEmpresa(String cif) {
        return empresaRepository.findById(cif).orElse(null);
    }

    // ----------------------------------------- POST -----------------------------------------
    public void createProducto(Producto producto) {
        productoRepository.save(producto);
    }

    public void createUser(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void createEmpresa(Empresa empresa) {
        empresaRepository.save(empresa);
    }

    public void createMaquina(Maquina maquina) {
        maquinaRepository.save(maquina);
    }

    // ----------------------------------------- PUT -----------------------------------------
    public void updateProducto(Producto producto) {
        productoRepository.save(producto);
    }

    public void updateMaquina(Maquina maquina) {
        maquinaRepository.save(maquina);
    }

    public void updateStock(MaquinaHasProducto mhp) {
        stockRepository.save(mhp);
    }

    // ----------------------------------------- DELETE -----------------------------------------
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
