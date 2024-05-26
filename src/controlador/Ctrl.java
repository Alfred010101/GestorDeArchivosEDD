package controlador;

import clases.Archivo;
import clases.ListaCircularDoblementeLigada;
import clases.Multilista;
import clases.Nodo;
import clases.NodoArbol;
import clases.TablaHash;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import vista.VentanaPrincipal;

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
            Nodo retorno = Var.getMultilista().insertar(Var.getMultilista().getRaiz(), 0, splitPath(ruta + "nuevo"), nodo);
            Var.getMultilista().setRaiz(retorno);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    /*
     * Actualiza un archivo en la multilista
     */
    public static boolean actualizar(Nodo porActualizar, String nombre, String extencion, String autor, char tipo, int tamaño, String ruta)
    {
        try
        {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Archivo archivo = new Archivo(nombre, extencion, currentDateTime.format(formatter), autor, tipo, tamaño, ruta);
            Nodo nodo = new <Archivo>Nodo(nombre + ((extencion != null) ? extencion : ""), archivo);
            if (porActualizar != null && porActualizar.getAbajo() != null)
            {
                Nodo aux = porActualizar.getAbajo();
                do
                {
                    aux.setArriba(nodo);
                    aux = aux.getSiguiente();
                } while (aux != porActualizar.getAbajo());
                nodo.setAbajo(aux);
            }
            Var.getMultilista().cambiarRuta(nodo, ruta + nombre + "/");
//            Multilista.cambiarRuta(nodo, ruta + nombre + "/");
            Nodo retorno = Var.getMultilista().insertar(Var.getMultilista().getRaiz(), 0, splitPath(ruta + "nuevo"), nodo);
            Var.getMultilista().setRaiz(retorno);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    public static String obtenerFecha()
    {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
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
        String regexArchivo = "^[a-zA-Z][a-zA-Z0-9ñÑ_\\- ]*\\.[a-z]+$";
        Pattern patternArchivo = Pattern.compile(regexArchivo);
        Matcher matcherArchivo = patternArchivo.matcher(nombreArch);

        String regexCarpeta = "^[a-zA-Z][a-zA-Z0-9ñÑ_\\- ]*$";
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
            Nodo aux = nodoPadre.getSiguiente();
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
            } while (aux != nodoPadre.getSiguiente());
        }
    }

//    public static TablaHash cargarTablaHash(Multilista multilista)
//    {
//        TablaHash tablaAux = new TablaHash();
//        if (multilista.getRaiz() != null)
//        {
//            Nodo auxOrigen = multilista.getRaiz();
//            do
//            {
//                cargarTablaHash(auxOrigen, tablaAux);
//                auxOrigen = auxOrigen.getSiguiente();
//            } while (auxOrigen != multilista.getRaiz());
//            
//        }   
//        return tablaAux;
//    }
//    
//    private static void cargarTablaHash(Nodo nodo, TablaHash tablaHash)
//    {
//        if (nodo != null && nodo.getAbajo() != null)
//        {
//            Nodo auxOrigen = nodo.getAbajo();
//            do
//            {
//                if (auxOrigen.getObjecto() instanceof Archivo x)
//                {
//                    if (x.getTipo() == 'A')
//                    {
//                        tablaHash.inserta(new NodoArbol(nodo.getEtiqueta(), nodo));
//                    } else
//                    {
//                        cargarTablaHash(nodo, tablaHash);
//                    }
//                }
//                auxOrigen = auxOrigen.getSiguiente();
//            } while (auxOrigen != nodo.getAbajo());
//        }
//    }
    public static TablaHash cargarTablaHash(Multilista multilista)
    {
        TablaHash tablaAux = new TablaHash();
        if (multilista.getRaiz() != null)
        {
            cargarNodosEnTablaHash(multilista.getRaiz(), tablaAux);
        }
        return tablaAux;
    }

    private static void cargarNodosEnTablaHash(Nodo nodo, TablaHash tablaHash)
    {
        if (nodo != null)
        {
            Nodo aux = nodo;
            do
            {
                tablaHash.inserta(new NodoArbol(aux.getEtiqueta(), aux));
                if (aux.getAbajo() != null)
                {
                    cargarNodosEnTablaHash(aux.getAbajo(), tablaHash);
                }
                aux = aux.getSiguiente();
            } while (aux != nodo);
        }
    }

    public static void actualizarRegistrosInterfaz(String[] rutaAct, String nom, boolean actualizarTablaHash)
    {
        if (rutaAct != null)
        {
            if (rutaAct.length > 0)
            {
                Nodo directorio = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, rutaAct, rutaAct[rutaAct.length - 1]);
                VentanaPrincipal.modelTabla.actualizarTabla(Ctrl.cargarDirectorio(directorio.getAbajo()));
            } else
            {
                VentanaPrincipal.modelTabla.actualizarTabla(Ctrl.cargarDirectorio(Var.getMultilista().getRaiz()));
            }
            if (!nom.contains("."))
            {
                VentanaPrincipal.rootNodoDirectorios.removeAllChildren();
                Ctrl.cargarArbolCarpetas(VentanaPrincipal.rootNodoDirectorios, Var.getMultilista().getRaiz());
                ((DefaultTreeModel) VentanaPrincipal.treeDirectorios.getModel()).reload(VentanaPrincipal.rootNodoDirectorios);
            }
            if (actualizarTablaHash)
            {
                Var.setTablaHash(Ctrl.cargarTablaHash(Var.getMultilista()));
                Var.getTablaHash().balanciar();
            }
            Var.banderaEliminarMultilista = false;
            Var.banderaInsersionMultilista = false;
        }
    }

    public static void copiarNodo()
    {
        Nodo nodo = Var.nodoCopiarBuffer.clonar(true); //clono el directorio padre
        ((Archivo) nodo.getObjecto()).setRuta(Var.rutaActual); //asigno la ruta actual
        //inserta el directorio padre en la lista 
        Nodo retorno = Var.getMultilista().insertar(Var.getMultilista().getRaiz(), 0, splitPath(Var.rutaActual + "copiar"), nodo);
        Var.getMultilista().setRaiz(retorno);
        if (Var.banderaInsersionMultilista)
        {
            if (Var.nodoCopiarBuffer.getAbajo() != null)               //pregunta si tiene subdirectorios
            {
                Var.getMultilista().setRaiz(copiarSubdirectorios(Var.nodoCopiarBuffer, Var.rutaActual + nodo.getEtiqueta() + "/"));
            }

            actualizarRegistrosInterfaz(splitPath(Var.rutaActual), nodo.getEtiqueta(), false);
            ManipulacionArchivos.guardar(Var.getMultilista(), "datos.dat");
        }

    }

    private static Nodo copiarSubdirectorios(Nodo origen, String ruta)
    {
        if (origen.getAbajo() != null)
        {
            //se asigna la  raiz
            Nodo auxOrigen = origen.getAbajo();

            do
            {

                Nodo insertar = auxOrigen.clonar(true); //se clona el nodo abajo y/o siguinete
                ((Archivo) insertar.getObjecto()).setRuta(ruta);     //se asigna su nueva ruta
                Nodo auxRetorno = Var.getMultilista().insertar(Var.getMultilista().getRaiz(), 0, splitPath(ruta + auxOrigen.getEtiqueta()), insertar);
                Var.getMultilista().setRaiz(auxRetorno);

                if (auxOrigen.getAbajo() != null) //pregunta si hay mas subdirectorios
                {
                    Var.getMultilista().setRaiz(copiarSubdirectorios(auxOrigen, ruta + auxOrigen.getEtiqueta() + "/"));
                }

                auxOrigen = auxOrigen.getSiguiente();

            } while (auxOrigen != origen.getAbajo());

            return Var.getMultilista().getRaiz();

        }
        return null;
    }

    public static String obtenerRutaSinRaiz(TreePath path)
    {
        String ruta = "";
        for (Object object : path.getPath())
        {
            ruta += object.toString() + "/";
        }
        int pos = ruta.indexOf("/");
        return ruta.substring(pos + 1);
    }

//    private static Nodo copiarSubdirectorios(Nodo origen, String ruta)
//    {
//        if (origen.getAbajo() != null)
//        {
//            Nodo auxOrigen = origen.getAbajo(); 
//            Multilista lista = new Multilista();
//            do{
//                lista.setRaiz(lista.insertar(lista.getRaiz(), 0, splitPath(ruta), auxOrigen.clonar(true)));
//                if (auxOrigen.getAbajo() != null)
//                {
//                    Nodo auxRetorno = lista.buscar(lista.getRaiz(), auxOrigen.getEtiqueta());
//                    auxRetorno.setAbajo(copiarSubdirectorios(auxOrigen, auxOrigen.getEtiqueta() + "/"));
//                }
//                auxOrigen = auxOrigen.getSiguiente();
//            }while(auxOrigen != origen.getAbajo());
//            return lista.getRaiz();
//        }
//        return null;
//    }
}
