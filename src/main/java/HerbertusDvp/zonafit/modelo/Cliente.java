package HerbertusDvp.zonafit.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity // Entidad JPA Que se maperará
@Data   // Crea los metodos set y get
@NoArgsConstructor  // Contructor vacio
@AllArgsConstructor // COntrunctor con todos los argumentos
@ToString
@EqualsAndHashCode

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer membresia;

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", membresia=" + membresia +
                '}';
    }
}
