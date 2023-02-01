package picapoint.picapointServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import picapoint.picapointServer.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findByUsernameAndPassword(String username, String password);
}
