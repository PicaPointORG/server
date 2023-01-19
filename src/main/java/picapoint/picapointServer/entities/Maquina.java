package picapoint.picapointServer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "maquina")
public class Maquina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ubicacion", nullable = false, length = 45)
    private String ubicacion;
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "empresa_cif", nullable = false)
    private Empresa empresa;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "maquina_has_producto",
            joinColumns = @JoinColumn(name = "maquina_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productos;
}