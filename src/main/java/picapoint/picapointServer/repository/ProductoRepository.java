package picapoint.picapointServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import picapoint.picapointServer.entities.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByEmpresaCif(String cif);
}
