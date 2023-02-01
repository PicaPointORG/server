package picapoint.picapointServer.entities;

import com.fasterxml.jackson.annotation.*;
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
    @Column(name = "precio", nullable = false)
    private double precio;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "empresa_cif", referencedColumnName = "cif", nullable = false)
    private Empresa empresa;

    @JsonManagedReference
    @OneToMany(mappedBy = "producto", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<MaquinaHasProducto> maquinaHasProductos = new ArrayList<>();
}