
package controlador;
import clases.Multilista;
import javax.swing.JLabel;

/**
 *
 * @author Alfred
 */

public class Var
{
    private static Multilista multilista = new Multilista();
    public static JLabel contador = new JLabel("Directorio vacio        ");
    public static String rutaActual = "";
    
    /**
     * @return the multilista
     */
    public static Multilista getMultilista()
    {
        return multilista;
    }

    /**
     * @param aMultilista the multilista to set
     */
    public static void setMultilista(Multilista aMultilista)
    {
        multilista = aMultilista;
    }    
}
