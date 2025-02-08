package HerbertusDvp.zonafit.controlador;

import HerbertusDvp.zonafit.modelo.Cliente;
import HerbertusDvp.zonafit.servicio.IClienteServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.primefaces.PrimeFaces;
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
    private Cliente clienteSeleccionado;
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

    public void agregarCliente(){
        this.clienteSeleccionado = new Cliente();
    }

    public void guardarCliente(){
        logger.info("cliente a guardar: "+ this.clienteSeleccionado);
        // Agregar
        if (this.clienteSeleccionado.getId() == null){
            this.clienteServicio.guardarCliente(this.clienteSeleccionado);
            this.clientes.add(this.clienteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Cliente agregado"));
        } else{ //Para modificar
            this.clienteServicio.guardarCliente(this.clienteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Cliente actualizado"));
        }
        // Ocultar la ventana modal
        PrimeFaces.current().executeScript("PF('ventanaModalCliente').hide()");
        // Actualizar la tabla usando ajax
        PrimeFaces.current().ajax().update(
                "forma-clientes:mensajes",
                "forma-clientes:clientes-tabla");
        // Reset del objeto seleccionado
        this.clienteSeleccionado = null;
    }

    public void eliminarCliente(){
        logger.info("Cliente a eliminar: "+ this.clienteSeleccionado);
        this.clienteServicio.eliminarCliente(this.clienteSeleccionado);
        // Eliminar el registro de la lista de clientes
        this.clientes.remove(this.clienteSeleccionado);
        // Reinicio del objeto eliminado
        this.clienteSeleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Cliente eliminado"));
        PrimeFaces.current().ajax().update("forma-clientes:mensages",
                "forma-clientes:clientes-tabla");
    }
}
