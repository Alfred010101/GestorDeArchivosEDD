package controlador;

import clases.Archivo;
import clases.Nodo;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Alfred
 */
public class Ctrl
{

    /*
     * Crea e inserta el nuevo archivo en la multilista
     */
    public static boolean crear(String nombre, String extencion, String autor, char tipo, int tamaño, String ruta)
    {
        try
        {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Archivo archivo = new Archivo(nombre, extencion, currentDateTime.format(formatter), autor, tipo, tamaño, ruta);
            Nodo nodo = new <Archivo>Nodo(nombre + ((extencion != null) ? extencion : ""), archivo);
            Var.getMultilista().setRaiz(Var.getMultilista().insertar(Var.getMultilista().getRaiz(), 0, splitPath(ruta + "nuevo"), nodo));
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    /**
     *
     * @param nombreArch cadena a evaluar
     * @param tipo tipo de evaluacion Carpeta o Archivo
     * @return devuelde en la primera posicion el nombre en la segunda posicion
     * la extencion a en caso de ser Archivo, si Carpeta devuele null
     */
    public static String[] validarNombre(String nombreArch, char tipo)
    {
        String regexArchivo = "^[a-zA-Z][a-zA-Z0-9ñÑ_\\-() ]*\\.[a-z]+$";
        Pattern patternArchivo = Pattern.compile(regexArchivo);
        Matcher matcherArchivo = patternArchivo.matcher(nombreArch);

        String regexCarpeta = "^[a-zA-Z][a-zA-Z0-9ñÑ_\\-() ]*$";
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

    /**
     *
     * @param s
     * @return si es dato esta soportado
     */
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

    /**
     *
     * @param ke evento
     * @param obj a que componente se dirije despues del enter
     */
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

    /**
     *
     * @param raiz indica el directorio actual
     * @return devuelve una lista con todos los elementos del directorio actual
     */
    public static List cargarDirectorio(Nodo raiz)
    {
        List<Archivo> listaArray = new ArrayList<>();
        if (raiz != null)
        {
            Nodo aux = raiz;
            do
            {
                aux = aux.getSiguiente();
                if (aux.getObjecto() instanceof Archivo x)
                {
                    listaArray.add(x);
                }
            } while (aux != raiz);
        }
        return listaArray;
    }

    /**
     *
     * @param path es el directorio a separar
     * @return devuelve un arreglo de Strings
     */
    public static String[] splitPath(String path)
    {
        // split para dividir la ruta en base al delimitador "/"
        String[] splitDir = path.split("/");
        ArrayList<String> listaDirectorios = new ArrayList<>();
        for (String dir : splitDir)
        {
            if (!dir.isEmpty())
            {
                listaDirectorios.add(dir);
            }
        }

        // Convertimos el ArrayList en un arreglo de String
        String[] directorios = new String[listaDirectorios.size()];
        directorios = listaDirectorios.toArray(directorios);
        return directorios;
    }
    
    public static void cargarArbolCarpetas(DefaultMutableTreeNode nodoRaiz, Nodo nodoPadre)
    {
        if (nodoPadre != null)
        {
            Nodo aux = nodoPadre;
            do
            {
                if (aux.getObjecto() instanceof Archivo x)
                {
                    if (x.getTipo() == 'C')
                    {
                        //Crear un nodo para representar la carpeta
                        DefaultMutableTreeNode nodoHijo = new DefaultMutableTreeNode(aux.getEtiqueta());
                        // Agregar el nodo al árbol
                        nodoRaiz.add(nodoHijo);
                        // Recursivamente agregar los subniveles de la carpeta
                        cargarArbolCarpetas(nodoHijo, aux.getAbajo());
                    }
                }
                aux = aux.getSiguiente();
            } while (aux != nodoPadre);
        }
    }
}
