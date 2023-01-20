package picapoint.picapointServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import picapoint.picapointServer.entities.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, String> {
}
