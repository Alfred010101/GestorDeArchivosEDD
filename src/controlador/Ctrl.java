package controlador;

import clases.Archivo;
import clases.Nodo;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author Alfred
 */
public class Ctrl
{

    public static boolean crear(String nombre, String extencion, String autor, char tipo, int tamaño, String ruta)
    {
        try
        {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Archivo archivo = new Archivo(nombre , nombre, currentDateTime.format(formatter), autor, tipo, tamaño, ruta);
            Nodo nodo = new <Archivo>Nodo(nombre + extencion, archivo);
            Var.getLista().insertar(nodo);
            return true;
        }catch(Exception e)
        {
            return false;
        }
        
    }

    public static String[] validarNombre(String nombreArch, char tipo)
    {
        String regexArchivo = "^[a-zA-Z0-9ñÑ_\\-()]+\\.[a-z]+$";
        Pattern patternArchivo = Pattern.compile(regexArchivo);
        Matcher matcherArchivo = patternArchivo.matcher(nombreArch);

        String regexCarpeta = "^[a-zA-Z0-9ñÑ_\\-()]+$";
        Pattern patternCarpeta = Pattern.compile(regexCarpeta);
        Matcher matcherCarpeta = patternCarpeta.matcher(nombreArch);

        boolean entradaValida = (tipo == 'A') ? matcherArchivo.matches() : matcherCarpeta.matches();
        if (entradaValida)
        {
            int index = nombreArch.lastIndexOf('.');

            // Si no se encuentra un punto, no hay extensión
            if (index == -1)
            {
                return new String[]
                {
                    nombreArch, null
                };
            }
            
            String name = nombreArch.substring(0, index);
            String extension = nombreArch.substring(index);

            return new String[]
            {
                name, extension
            };
        }
        return null;
    }

    public static int esNumeroValido(String s)
    {
        try
        {
            int a = Integer.parseInt(s);
            if (a > 0)
            {
                return a;
            }
        } catch (NumberFormatException e)
        {
        } catch (Exception e)
        {
        }
        return -1;
    }

    public static void enter(KeyEvent ke, Object obj)
    {
        if (ke.getKeyChar() == '\n')
        {
            if (obj != null)
            {
                if (obj instanceof JTextField jt)
                {
                    jt.setSelectionStart(0);
                    jt.setSelectionEnd(jt.getText().length());
                    jt.requestFocus();
                }
                if (obj instanceof JButton jt)
                {
                    jt.requestFocus();
                }
            }
        }
    }
}
