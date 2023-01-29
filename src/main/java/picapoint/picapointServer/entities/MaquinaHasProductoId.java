package picapoint.picapointServer.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class MaquinaHasProductoId implements Serializable {
    @Serial
    private static final long serialVersionUID = -3112953729008570664L;
    @Column(name = "maquina_id", nullable = false)
    private Long maquinaId;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MaquinaHasProductoId entity = (MaquinaHasProductoId) o;
        return Objects.equals(this.productoId, entity.productoId) &&
                Objects.equals(this.maquinaId, entity.maquinaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productoId, maquinaId);
    }

}
