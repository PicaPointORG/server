package picapoint.picapointServer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "empresa")
public class Empresa {
    @Id
    @Column(name = "cif", nullable = false, length = 9)
    private String id;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "telefono", nullable = false)
    private Integer telefono;

    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Column(name = "direccion", nullable = false, length = 45)
    private String direccion;

    @Column(name = "codigo_postal", nullable = false)
    private Integer codigoPostal;
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.MERGE)
    private List<Maquina> maquinas = new ArrayList<>();
}