
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
    private static JLabel contador = new JLabel("Directorio vacio        ");
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

    /**
     * @return the contador
     */
    public static JLabel getContador()
    {
        return contador;
    }

    /**
     * @param aContador the contador to set
     */
    public static void setContador(JLabel aContador)
    {
        contador = aContador;
    }
}
