
package controlador;
import clases.Multilista;
import clases.TablaHash;
import javax.swing.JLabel;

/**
 *
 * @author Alfred
 */

public class Var
{
    /**
     * Mantiene la MULTILISTA principal empleada por el programa
     */
    private static Multilista multilista = new Multilista();
    
    /**
     * Mantiene la TABLA HASH principal empleada para las busquedas
     */
    private static TablaHash tablaHash = new TablaHash();
    
    //esta se va a mover
    public static JLabel contador = new JLabel("Directorio vacio        ");
    
    /**
     * Mantiene el registo de la ruta donde se encuentra actualmente
     */
    public static String rutaActual = "";
    
    /**
     * Banera empleada para notificar el estado de una insersion 
     * en la multilistaprincipal
     */
    public static boolean banderaInsersionMultilista = false;
    
    /**
     * Banera empleada para notificar el estado de una eliminacion 
     * en la multilistaprincipal
     */
    public static boolean banderaEliminarMultilista = false;
    
    /**
     * Proporciona la ruta para encontrar los recusros(imagenes) 
     * empleadas en el diseño del sistema
     */
    public static final String PATH_IMAGENES = "src/vista/imagenes/";
   
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
     * @return the tablaHash
     */
    public static TablaHash getTablaHash()
    {
        return tablaHash;
    }

    /**
     * @param aTablaHash the tablaHash to set
     */
    public static void setTablaHash(TablaHash aTablaHash)
    {
        tablaHash = aTablaHash;
    }
}
