package picapoint.picapointServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import picapoint.picapointServer.entities.Maquina;

import java.util.List;

public interface MaquinaRepository extends JpaRepository<Maquina, Long> {
    List<Maquina> findByEmpresaCif(String cif);

}
