package HerbertusDvp.zonafit.controlador;

import HerbertusDvp.zonafit.modelo.Cliente;
import HerbertusDvp.zonafit.servicio.IClienteServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component // Determina que es un elemento de sprig
@Data  // Para los metodos get y set
@ViewScoped

public class IndexControlador {

    @Autowired
    IClienteServicio clienteServicio;
    private List<Cliente> clientes;
    //Para poder mandar salidas a la consola
    private static final Logger logger =
            LoggerFactory.getLogger(IndexControlador.class);

    @PostConstruct // Costructor dado por jsf
    public void init(){
        cargarDatos();
    }

    public void cargarDatos(){
        logger.info("");
        this.clientes = this.clienteServicio.listarClientes();
        this.clientes.forEach(cliente -> logger.info(cliente.toString()));
    }
}
