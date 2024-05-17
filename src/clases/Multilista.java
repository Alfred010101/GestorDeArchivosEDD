package clases;

/**
 *
 * @author Alfred
 */
public class Multilista
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
                lista.insertar(nodo);
            } else
            {
                System.out.println("Ya existe un archivo con el mismo nombre");
            }

            return lista.getRaiz();
        } else
        {
            Nodo aux = buscar(nodoRaiz, ruta[nivel]);
            if (aux != null)
            {
                if (buscar(aux.getAbajo(), ruta[nivel]) == null)
                {
                    aux.setAbajo(insertar(aux.getAbajo(), nivel + 1, ruta, nodo));
                    aux.getAbajo().setArriba(aux);
                } else
                {
                    System.out.println("Ya existe un archivo con el mismo nombre");
                }
            } else
            {
                System.out.println("El directorio no existe");
            }
            return nodoRaiz;
        }
    }

    public Nodo buscar(Nodo nodoRaiz, int nivel, String[]ruta, String etq)
    {
        if (ruta.length - 1 == nivel)
        {
            return buscar(nodoRaiz, etq);
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
                if (aux.getEtiqueta().equals(etq))
                {
                    return aux;
                }
                aux = aux.getSiguiente();
            } while (aux != nodoRaiz);
        }
        return null;
    }
}
