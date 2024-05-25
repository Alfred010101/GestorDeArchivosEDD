package clases;

import controlador.Var;
import java.io.Serializable;
import javax.swing.JOptionPane;

/**
 *
 * @author Alfred
 */
public class Multilista implements Serializable
{

    private Nodo raiz = null;

    /**
     * @return the raiz
     */
    public Nodo getRaiz()
    {
        return raiz;
    }

    /**
     * @param raiz the raiz to set
     */
    public void setRaiz(Nodo raiz)
    {
        this.raiz = raiz;
    }

    public Nodo insertar(Nodo nodoRaiz, int nivel, String[] ruta, Nodo nodo)
    {
        if (ruta.length - 1 == nivel)
        {
            ListaCircularDoblementeLigada lista = new ListaCircularDoblementeLigada();
            lista.setRaiz(nodoRaiz);
            if (lista.buscarNodo(nodo.getEtiqueta()) == null)
            {
                if (nodoRaiz != null && nodoRaiz.getArriba() != null)
                {
                    nodo.setArriba(nodoRaiz.getArriba());
                }
                lista.insertar(nodo);
                Var.getTablaHash().inserta(new NodoArbol(nodo.getEtiqueta(), nodo));
                Var.getTablaHash().balanciar(nodo.getEtiqueta());
                Var.banderaInsersionMultilista = true;
            } else
            {
                JOptionPane.showMessageDialog(null, "Ya existe un archivo con el nombre \"" + nodo.getEtiqueta() +"\".", "Advertencia", JOptionPane.WARNING_MESSAGE); 
            }

            return lista.getRaiz();
        } else
        {
            Nodo aux = buscar(nodoRaiz, ruta[nivel]);
            if (aux != null)
            {
                aux.setAbajo(insertar(aux.getAbajo(), nivel + 1, ruta, nodo));
                aux.getAbajo().setArriba(aux);
            } else
            {
                JOptionPane.showMessageDialog(null, "El directorio no existe.", "Advertencia", JOptionPane.WARNING_MESSAGE);     
            }
            return nodoRaiz;
        }
    }
    
//    public Nodo elimina(Nodo nodoRaiz, int nivel, String[] ruta, Nodo nodo)
//    {
//        if (ruta.length - 1 == nivel)
//        {
//            ListaCircularDoblementeLigada lista = new ListaCircularDoblementeLigada();
//            lista.setRaiz(nodoRaiz);
//            if (lista.buscarNodo(nodo.getEtiqueta()) != null)
//            {
//                lista.eliminar(nodo.getEtiqueta());
//            } else
//            {
//                System.out.println("No se encontró el archivo a eliminar");
//            }
//            return lista.getRaiz();
//        } else
//        {
//            Nodo aux = buscar(nodoRaiz, ruta[nivel]);
//
//            if (aux != null)
//            {
//                aux.setAbajo(elimina(aux.getAbajo(), nivel + 1, ruta, nodo));
//                if (aux.getAbajo() != null)
//                {
//                    aux.getAbajo().setArriba(aux);
//                }
//            } else
//            {
//                System.out.println("No se ecnotró el archivo en el nivel");
//            }
//            return nodoRaiz;
//        }
//    }
    
    public Nodo elimina(Nodo nodoRaiz, int nivel, String[] ruta, String etq)
    {
        if (ruta.length - 1 == nivel)
        {
            ListaCircularDoblementeLigada lista = new ListaCircularDoblementeLigada();
            lista.setRaiz(nodoRaiz);
            if (lista.buscarNodo(etq) != null)
            {
                Nodo nodoEliminado = lista.eliminar(etq);
                if (nodoEliminado != null)
                {
                    nodoEliminado.setArriba(null);
                    Var.banderaEliminarMultilista = true;
                }
            } else
            {
                System.out.println("No se encontró el archivo a eliminar");
            }
            return lista.getRaiz();
        } else
        {
            Nodo aux = buscar(nodoRaiz, ruta[nivel]);

            if (aux != null)
            {
                aux.setAbajo(elimina(aux.getAbajo(), nivel + 1, ruta, etq));
                if (aux.getAbajo() != null)
                {
                    aux.getAbajo().setArriba(aux);
                }
            } else
            {
                System.out.println("No se ecnotró el archivo en el nivel");
            }
            return nodoRaiz;
        }
    }

    public Nodo buscar(Nodo nodoRaiz, int nivel, String[] ruta, String etq)
    {
        if (ruta.length - 1 == nivel)
        {
            Nodo nodo = buscar(nodoRaiz, etq);
            return nodo;
        } else
        {
            Nodo aux = buscar(nodoRaiz, ruta[nivel]);
            if (aux != null)
            {
                return buscar(aux.getAbajo(), nivel + 1, ruta, etq);
            }
        }
        return null;
    }

    public Nodo buscar(Nodo nodoRaiz, String etq)
    {
        if (nodoRaiz != null)
        {
            Nodo aux = nodoRaiz;
            do
            {
                if (aux.getEtiqueta().equalsIgnoreCase(etq))
                {
                    return aux;
                }
                aux = aux.getSiguiente();
            } while (aux != nodoRaiz);
        }
        return null;
    }

    public int calcularTamanio(Nodo nodo, int tamanio)
    {
        if (nodo != null && nodo.getAbajo() != null)
        {
            Nodo aux = nodo.getAbajo();
            do
            {
                if (aux.getObjecto() instanceof Archivo x)
                {
                    if (x.getTipo() == 'A')
                    {
                        tamanio += x.getTamanio();
                    } else
                    {
                        tamanio = calcularTamanio(aux, tamanio);
                    }
                    if (tamanio < 0)
                    {
                        return tamanio;
                    }
                }
                aux = aux.getSiguiente();
            } while (aux != nodo.getAbajo());
        }
        return tamanio;
    }
    
    public void cambiarRuta(Nodo rutaAnterior, String rutaNueva)
    {
        if (rutaAnterior != null && rutaAnterior.getAbajo() != null)
        {
            Nodo aux = rutaAnterior.getAbajo();
            do
            {
                if (aux.getObjecto() instanceof Archivo x)
                {
                    x.setRuta(rutaNueva);
                    if(x.getTipo() == 'C')
                    {
                        cambiarRuta(aux, rutaNueva + x.getNombre() + "/");
                    }
                }
                aux = aux.getSiguiente();
            } while (aux != rutaAnterior.getAbajo());
        }
    }
}
