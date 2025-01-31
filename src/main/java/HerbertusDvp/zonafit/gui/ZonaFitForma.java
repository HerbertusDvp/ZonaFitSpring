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
        setSize(900, 700);
        setLocationRelativeTo(null); // centrar ventana
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloCliente = new DefaultTableModel(0, 4);
        String campos [] = {"Id", "Nombre", "Apellido","Membresia"};
        this.tablaModeloCliente.setColumnIdentifiers(campos);
        this.tCliente = new JTable(tablaModeloCliente);
    }
}
