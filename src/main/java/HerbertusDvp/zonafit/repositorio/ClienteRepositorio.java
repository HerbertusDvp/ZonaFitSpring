package HerbertusDvp.zonafit.repositorio;

import HerbertusDvp.zonafit.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
}
