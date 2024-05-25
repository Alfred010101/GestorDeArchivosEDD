
package clases;

/**
 *
 * @author Alfred
 * @param <T>
 */
public class NodoArbol <T>
{
    private String etq;
    private T nodoHilado;
    
    private NodoArbol<T> izq = null;
    private NodoArbol<T> der = null;
    
    public NodoArbol(String etq, T nodoHilado)
    {
        this.etq = etq;
        this.nodoHilado = nodoHilado;
    }

    /**
     * @return the etq
     */
    public String getEtq()
    {
        return etq;
    }

    /**
     * @param etq the etq to set
     */
    public void setEtq(String etq)
    {
        this.etq = etq;
    }

    /**
     * @return the obj
     */
    public T getObj()
    {
        return nodoHilado;
    }

    /**
     * @param nodoHilado the obj to set
     */
    public void setObj(T nodoHilado)
    {
        this.nodoHilado = nodoHilado;
    }

    /**
     * @return the izq
     */
    public NodoArbol<T> getIzq()
    {
        return izq;
    }

    /**
     * @param izq the izq to set
     */
    public void setIzq(NodoArbol<T> izq)
    {
        this.izq = izq;
    }

    /**
     * @return the der
     */
    public NodoArbol<T> getDer()
    {
        return der;
    }

    /**
     * @param der the der to set
     */
    public void setDer(NodoArbol<T> der)
    {
        this.der = der;
    }
}
