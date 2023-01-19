package picapoint.picapointServer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;
    @ManyToMany(mappedBy = "productos", cascade = CascadeType.MERGE)
    private List<Maquina> maquinas;
}