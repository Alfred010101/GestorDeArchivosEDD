
package clases;

import controlador.Ctrl;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Alfred
 * @param <T>
 */

public class Nodo<T> implements Serializable
{
    private String etiqueta;
    private T objecto;
    private Nodo siguiente;
    private Nodo anterior;
    private Nodo arriba;
    private Nodo abajo;

    public Nodo(String etiqueta, T objecto)
    {
        this.etiqueta = etiqueta;
        this.objecto = objecto;
        siguiente = null;
        anterior = null;
        arriba = null;
        abajo = null;
    }

    /**
     * @return the etiqueta
     */
    public String getEtiqueta()
    {
        return etiqueta;
    }

    /**
     * @param etiqueta the etiqueta to set
     */
    public void setEtiqueta(String etiqueta)
    {
        this.etiqueta = etiqueta;
    }

    /**
     * @return the objecto
     */
    public T getObjecto()
    {
        return objecto;
    }

    /**
     * @param objecto the objecto to set
     */
    public void setObjecto(T objecto)
    {
        this.objecto = objecto;
    }

    /**
     * @return the siguiente
     */
    public Nodo getSiguiente()
    {
        return siguiente;
    }

    /**
     * @param siguiente the siguiente to set
     */
    public void setSiguiente(Nodo siguiente)
    {
        this.siguiente = siguiente;
    }

    /**
     * @return the anterior
     */
    public Nodo getAnterior()
    {
        return anterior;
    }

    /**
     * @param anterior the anterior to set
     */
    public void setAnterior(Nodo anterior)
    {
        this.anterior = anterior;
    }

    /**
     * @return the arriba
     */
    public Nodo getArriba()
    {
        return arriba;
    }

    /**
     * @param arriba the arriba to set
     */
    public void setArriba(Nodo arriba)
    {
        this.arriba = arriba;
    }

    /**
     * @return the abajo
     */
    public Nodo getAbajo()
    {
        return abajo;
    }

    /**
     * @param abajo the abajo to set
     */
    public void setAbajo(Nodo abajo)
    {
        this.abajo = abajo;
    }
    
    /**
     * 
     * @param actualizarFecha indica si se de concervar las fecha o actualizar
     * @return Devuelve un nodo con las misma caracteristicas del objeto que contine
     */
    public Nodo clonar(boolean actualizarFecha)
    {
        Archivo tmp = (Archivo)objecto;       
        Archivo clon = new Archivo(tmp.getNombre(), tmp.getExtension(), tmp.getFecha(), tmp.getAutor(), tmp.getTipo(), tmp.getTamanio(), tmp.getRuta());
         if (actualizarFecha)
        {
            clon.setFecha(Ctrl.obtenerFecha());
        }
        return new Nodo(this.etiqueta, clon);
    }
}
