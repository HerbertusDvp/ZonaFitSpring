package HerbertusDvp.zonafit;

import HerbertusDvp.zonafit.modelo.Cliente;
import HerbertusDvp.zonafit.servicio.IClienteServicio;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

//@SpringBootApplication
public class ZonafitApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger = LoggerFactory.getLogger(ZonafitApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando la aplicacion..");
		//Levantar la fabrica de spring par apoder ser utilizada
		SpringApplication.run(ZonafitApplication.class, args);
		logger.info("Apliccion finalizada..");
	}

	@Override
	public void run(String... args) throws Exception {
		zonaFitApp();
	}

	private void zonaFitApp(){
		var salir = false;
		var consola = new Scanner(System.in);
		while (!salir){
			var opcion = mostrarMenu(consola);
			salir = ejecutarOpciones(consola, opcion);
			logger.info(nl);
		}
	}

	private  int mostrarMenu(Scanner consola){
		logger.info("""
				** Zona fit con Spring **
				
				1. Listas clientes
				2. Buscar cliente
				3. Agregar cliente
				4. Modificar cliente
				5. Eliminar cliente
				6. Salir
				elige una opcion: \s
				""");
		return Integer.parseInt(consola.nextLine());

	}

	private  boolean ejecutarOpciones(Scanner consola, int opcion){
		var salir = false;
		switch (opcion){
			case 1 ->{
				logger.info(nl+"++ Listado de clientes ++"+nl);
				List<Cliente> clientes = clienteServicio.listarClientes();
				clientes.forEach(cliente -> logger.info(cliente.toString()+nl));
			}
			case 2 ->{
				logger.info(nl+"++ Buscar cliente "+nl);
				logger.info("Id del cliente a buscar: ");
				var idCliente = Integer.parseInt(consola.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null)
					logger.info("Cliente encontrado: "+cliente);
				else
					logger.info("Cliente no encontrado: "+cliente);
			}
			case 3->{
				logger.info(nl+"++ Agregar estudiante ++"+nl);
				logger.info("Ingresa Nombre: ");
				var nombre = consola.nextLine();
				logger.info("Ingresa Apellido: ");
				var apellido = consola.nextLine();
				logger.info("Ingresa Membresia: ");
				var membresia = Integer.parseInt(consola.nextLine());
				Cliente cliente = new Cliente();

				cliente.setNombre(nombre);
				cliente.setApellido(apellido);
				cliente.setMembresia(membresia);
				clienteServicio.guardarCliente(cliente);

				logger.info("Cliente agregado: "+cliente);

			}
			case 4->{
				logger.info(nl+"++ Modificar cliente"+nl);
				logger.info("Ingresa el id del cliente: ");

				var idCliente = Integer.parseInt(consola.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);

				if (cliente != null){
					logger.info("Nombre: ");
					var nombre = consola.nextLine();
					logger.info("Apellido: ");
					var apellido = consola.nextLine();
					logger.info("Membresia: ");
					var membresia = Integer.parseInt(consola.nextLine());

					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setMembresia(membresia);

					clienteServicio.guardarCliente(cliente);

					logger.info("Cliente modificado: "+ cliente);
				}else {
					logger.info("CLiente no encontrado: "+ cliente);
				}
			}
			case 5->{
				logger.info(nl+"++ Eliminar cliente ++"+nl);
				logger.info("Ingresa el id del cliente: ");
				var idCliente = Integer.parseInt(consola.nextLine());
				var cliente = clienteServicio.buscarClientePorId(idCliente);

				if (cliente != null){
					clienteServicio.eliminarCliente(cliente);
					logger.info("Cliente eliminado: "+cliente);
				}else
					logger.info("Cliente no encontrado: "+cliente+nl);
			}
			case 6->{
				logger.info("Hasta pronto..");
				salir = true;
			}
			default -> logger.info(nl+"Opcion no existe.."+nl);
		}
		return salir;
	}
}
