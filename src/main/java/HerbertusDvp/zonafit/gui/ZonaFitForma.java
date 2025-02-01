package HerbertusDvp.zonafit.gui;

import HerbertusDvp.zonafit.servicio.ClienteServicio;
import HerbertusDvp.zonafit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Component
public class ZonaFitForma extends JFrame{
    private JPanel pPrincipal;
    private JPanel pTabla;
    private JTable tCliente;
    private JTextField tfNombre;
    private JLabel lNombre;
    private JLabel lApellido;
    private JTextField tfApellido;
    private JTextField tfMembresia;
    private JButton bGuardar;
    private JButton bLimpiar;
    private JButton bEliminar;
    private JPanel pFormulario;
    private JPanel pBotones;
    IClienteServicio clienteServicio;
    private DefaultTableModel tablaModeloCliente;

    @Autowired
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();
    }

    private void iniciarForma(){
        setContentPane(pPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // centrar ventana
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloCliente = new DefaultTableModel(0, 4);
        String campos [] = {"Id", "Nombre", "Apellido","Membresia"};
        this.tablaModeloCliente.setColumnIdentifiers(campos);
        this.tCliente = new JTable(tablaModeloCliente);
        // cargar listado de clientes
        listarClientes();
    }

    private void listarClientes(){
        this.tablaModeloCliente.setRowCount(0);

        var clientes = this.clienteServicio.listarClientes();

        clientes.forEach(cliente -> {
            Object[] rowsCliente = {
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getMembresia()
            };
            this.tablaModeloCliente.addRow(rowsCliente);
        });
    }
}
