package picapoint.picapointServer.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Table(name = "maquina")
public class Maquina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ubicacion", nullable = false, length = 400)
    private String ubicacion;
    @JsonBackReference
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "empresa_cif", nullable = false)
    private Empresa empresa;

    @JsonManagedReference
    @OneToMany(mappedBy = "maquina", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<MaquinaHasProducto> maquinaHasProductos = new ArrayList<>();
}