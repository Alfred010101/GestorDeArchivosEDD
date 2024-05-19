
package controlador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Alfred
 */

public class ManipulacionArchivos
{

    private static final String RUTA = "src/BD/";

    public static boolean guardar(Object obj, String s)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(RUTA + s);
            ObjectOutputStream arch = new ObjectOutputStream(fos);
            arch.writeObject(obj);
            arch.close();
            return true;
        } catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error al guardar", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error al guardar", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error al guardar", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public static Object cargar(String s)
    {
        Object obj = null;
        try
        {
            FileInputStream fis = new FileInputStream(RUTA + s);
            ObjectInputStream arch = new ObjectInputStream(fis);
            obj = arch.readObject();
            arch.close();
        } catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "El sistema no a econtrado el archivo " + RUTA + s + ".\nSi el archivo " + s + " aun no se a creado omita esta advertencia.", "Error al cargar", JOptionPane.WARNING_MESSAGE);
        } catch (IOException | ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error al cargar", JOptionPane.ERROR_MESSAGE);
        }
        return obj;
    }
}
