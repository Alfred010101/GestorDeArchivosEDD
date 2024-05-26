
package clases;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alfred
 */
public class TablaHash
{
    private ArbolBinario arr[]=new ArbolBinario[26];

    public TablaHash()
    {
        for (int i = 0; i < arr.length; i++)
        {
            arr[i]=new ArbolBinario();
        }
    }
    
    public void inserta(NodoArbol nodo)
    {
        if (nodo!=null)
        {
            int pos = nodo.getEtq().trim().toUpperCase().charAt(0)-65;
            arr[pos].setR(arr[pos].inserta(arr[pos].getR(), nodo)); 
        }
    }
    
    public NodoArbol busca(String et)
    {
        if (et.isEmpty())
        {
            return null;
        } else
        {
            int pos = et.trim().toUpperCase().charAt(0)-65;
            return arr[pos].busca(arr[pos].getR(), et);
        }
    }
    
    public List<Nodo> buscaNodos(String prefijo)
    {
        if (!prefijo.isEmpty())
        {
            int pos = prefijo.trim().toUpperCase().charAt(0)-65;
            if (pos >= 0 && pos <= 26)
            {
                return arr[pos].encontrarNodos(prefijo);
            }
        }
        return new ArrayList<>();
    }
    
    public void balanciar()
    {
        for (int i = 0; i < arr.length -1; i++)
        {
            if (arr[i].getR() != null)
            {
                arr[i].balancear();
            }
        }
    }
    
    public void balanciar(String etq)
    {
        int pos = etq.trim().toUpperCase().charAt(0)-65;
        arr[pos].balancear();
    }
}
