package picapoint.picapointServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import picapoint.picapointServer.entities.MaquinaHasProducto;
import picapoint.picapointServer.entities.MaquinaHasProductoId;

public interface StockRepository extends JpaRepository<MaquinaHasProducto, MaquinaHasProductoId> {
    MaquinaHasProducto findByMaquinaMacAndProductoId(String mac, Long idProducto);
}
