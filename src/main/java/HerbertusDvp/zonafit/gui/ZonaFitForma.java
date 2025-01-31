package HerbertusDvp.zonafit.gui;

import HerbertusDvp.zonafit.servicio.ClienteServicio;
import HerbertusDvp.zonafit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class ZonaFitForma extends JFrame{
    private JPanel pPrincipal;
    IClienteServicio clienteServicio;

    @Autowired
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
    }
}
