
package run;

import clases.Multilista;
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
