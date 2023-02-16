package picapoint.picapointServer.service;

import org.springframework.stereotype.Service;
import picapoint.picapointServer.entities.MaquinaHasProducto;
import picapoint.picapointServer.repository.*;
@Service
public class MaquinaService {
    private final EmpresaRepository empresaRepository;
    private final ProductoRepository productoRepository;
    private final MaquinaRepository maquinaRepository;
    private final UsuarioRepository usuarioRepository;
    private final StockRepository stockRepository;

    public MaquinaService(EmpresaRepository empresaRepository, ProductoRepository productoRepository, MaquinaRepository maquinaRepository, UsuarioRepository usuarioRepository, StockRepository stockRepository) {
        this.empresaRepository = empresaRepository;
        this.productoRepository = productoRepository;
        this.maquinaRepository = maquinaRepository;
        this.usuarioRepository = usuarioRepository;
        this.stockRepository = stockRepository;
    }

    public void updateMaquinaStock(String mac, Long idProducto){
        MaquinaHasProducto maquinaHasProducto = stockRepository.findByMaquinaMacAndProductoId(mac, idProducto);
        maquinaHasProducto.setStock(maquinaHasProducto.getStock() - 1);
        stockRepository.save(maquinaHasProducto);
    }
}
