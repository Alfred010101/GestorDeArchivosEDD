
package run;

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
