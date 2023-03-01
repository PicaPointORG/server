package picapoint.picapointServer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String username;
    private String email;
    private String password;
    private String rol;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_cif")
    private Empresa empresa;

}
