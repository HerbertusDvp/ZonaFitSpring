package HerbertusDvp.zonafit.gui;

import HerbertusDvp.zonafit.modelo.Cliente;
import HerbertusDvp.zonafit.servicio.ClienteServicio;
import HerbertusDvp.zonafit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//@Component
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
    private Integer idCliente;

    @Autowired
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();

        bGuardar.addActionListener(e -> {
            guardarCliente();
        });
        tCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClientes();
            }
        });

        bEliminar.addActionListener(e -> {
            eliminarCliente();
        });
        bLimpiar.addActionListener(e -> {
            limpiarFormulario();
        });
    }

    private void iniciarForma(){
        setContentPane(pPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // centrar ventana
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        //this.tablaModeloCliente = new DefaultTableModel(0, 4);
        //Evita la edicion de la tabla
        this.tablaModeloCliente = new DefaultTableModel(0,4){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        String campos [] = {"Id", "Nombre", "Apellido","Membresia"};
        this.tablaModeloCliente.setColumnIdentifiers(campos);
        this.tCliente = new JTable(tablaModeloCliente);
        // Retringir seleecion multiple de registros de la tabla
        this.tCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
    private void guardarCliente(){
        if (tfNombre.getText().equals("")){
            mostrarMensaje("Ingresa un nombre ");
            tfNombre.requestFocusInWindow();
            return;
        }

        if (tfMembresia.getText().equals("")){
            mostrarMensaje("Ingresa la membresia ");
            tfMembresia.requestFocusInWindow();
            return;
        }
        //Obtener vlaores del formulario
        var nombre = tfNombre.getText();
        var apellido = tfApellido.getText();
        var membresia = Integer.parseInt(tfMembresia.getText());

        var cliente = new Cliente(this.idCliente, nombre, apellido, membresia);

        this.clienteServicio.guardarCliente(cliente);

        if (this.idCliente == null)
            mostrarMensaje("Se agregó el cliente: "+cliente);
        else
            mostrarMensaje("El cliente se actualizó ");

        limpiarFormulario();
        listarClientes();

    }
    private void cargarClientes(){
        var renglon = tCliente.getSelectedRow();
        if (renglon != -1){
            var id = tCliente.getModel().getValueAt(renglon, 0).toString();
            this.idCliente = Integer.parseInt(id);
            var nombre = tCliente.getModel().getValueAt(renglon, 1).toString();
            this.tfNombre.setText(nombre);
            var apellido = tCliente.getModel().getValueAt(renglon, 2).toString();
            this.tfApellido.setText(apellido);
            var membresia = tCliente.getModel().getValueAt(renglon, 3).toString();
            this.tfMembresia.setText(membresia);
        }
    }

    private void eliminarCliente(){
        var renglon = tCliente.getSelectedRow();
        if (renglon != -1){
            var idClienteStr = tCliente.getModel().getValueAt(renglon, 0).toString();
            this.idCliente = Integer.parseInt(idClienteStr);

            var cliente = new Cliente();
            cliente.setId(this.idCliente);
            clienteServicio.eliminarCliente(cliente);
            mostrarMensaje("Cliente climinado con id: "+idCliente);
            limpiarFormulario();
            listarClientes();
        }else
            mostrarMensaje("Debes seleccionar un registro");
    }

    private void limpiarFormulario(){
        tfNombre.setText("");
        tfApellido.setText("");
        tfMembresia.setText("");
        this.idCliente = null;
        //Deseleccionar registro de la tabla

        this.tCliente.getSelectionModel().clearSelection();
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
