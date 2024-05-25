
package run;

import clases.Multilista;
import clases.TablaHash;
import controlador.Ctrl;
import controlador.ManipulacionArchivos;
import controlador.Var;
import java.awt.EventQueue;
import vista.VentanaPrincipal;

/**
 *
 * @author Alfred
 */

public class GestorArchivos
{
    public static void main(String[] args)
    {
        if (ManipulacionArchivos.cargar("datos.dat") instanceof Multilista x)
        {
            Var.setMultilista(x);
        } else
        {
            Var.setMultilista(new Multilista());
        }
        Var.setTablaHash(Ctrl.cargarTablaHash(Var.getMultilista()));
        Var.getTablaHash().balanciar();
        
        EventQueue.invokeLater(() ->
        {
            try
            {
                VentanaPrincipal ventana = new VentanaPrincipal();
                ventana.setVisible(true);
            } catch (Exception e)
            {
            }
        });
    }
}
