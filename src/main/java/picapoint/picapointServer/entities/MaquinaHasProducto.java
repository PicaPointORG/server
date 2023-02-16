package picapoint.picapointServer.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "maquina_has_producto")
public class MaquinaHasProducto {
    @JsonBackReference
    @EmbeddedId
    private MaquinaHasProductoId id;

    @JsonBackReference
    @MapsId("maquinaId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "maquina_id", nullable = false)
    private Maquina maquina;

    @JsonBackReference
    @MapsId("productoId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "stock", nullable = false)
    private Integer stock;
}
