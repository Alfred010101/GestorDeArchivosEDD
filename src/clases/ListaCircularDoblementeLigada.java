package clases;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alfred
 */

public class ListaCircularDoblementeLigada
{

    private Nodo raiz;

    //Expresion regular para saber si es una carpeta o un archivo
    private final Pattern pattern = Pattern.compile("^[^.]+\\.[^.]+$");

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

    public boolean esNull()
    {
        return raiz == null;
    }

    public boolean insertar(Nodo nodo)
    {
        if (nodo == null)
        {
            System.out.println("No se pueden insertar nodos nulos");
            return false;
        }

        if (raiz == null)
        {
            raiz = nodo;
            raiz.setSiguiente(raiz);
            raiz.setAnterior(raiz);
            return true;
        }

        Matcher matcher = pattern.matcher(nodo.getEtiqueta());
        boolean esArchivoElNodoAInsertar = matcher.matches();

        Matcher matcher2 = pattern.matcher(raiz.getSiguiente().getEtiqueta());
        boolean esArchivoElPrimerNodo = matcher2.matches();

        if ((!esArchivoElNodoAInsertar && esArchivoElPrimerNodo) || (!esArchivoElNodoAInsertar && !esArchivoElPrimerNodo && nodo.getEtiqueta().compareToIgnoreCase(raiz.getSiguiente().getEtiqueta()) < 0) || (esArchivoElNodoAInsertar && esArchivoElPrimerNodo && nodo.getEtiqueta().compareToIgnoreCase(raiz.getSiguiente().getEtiqueta()) < 0))
       {
            nodo.setSiguiente(raiz.getSiguiente());
            nodo.setAnterior(raiz);
            raiz.getSiguiente().setAnterior(nodo);
            raiz.setSiguiente(nodo);
            return true;
        }

        matcher2 = pattern.matcher(raiz.getEtiqueta());
        boolean esArchivoElUltimoNodo = matcher2.matches();

        if ((!esArchivoElUltimoNodo && esArchivoElNodoAInsertar) || (esArchivoElUltimoNodo && esArchivoElNodoAInsertar && nodo.getEtiqueta().compareToIgnoreCase(raiz.getEtiqueta()) > 0) || (!esArchivoElUltimoNodo && !esArchivoElNodoAInsertar && nodo.getEtiqueta().compareToIgnoreCase(raiz.getEtiqueta()) > 0))
       {
            nodo.setSiguiente(raiz.getSiguiente());
            nodo.setAnterior(raiz);
            raiz.getSiguiente().setAnterior(nodo);
            raiz.setSiguiente(nodo);
            raiz = nodo;
            return true;
        }

        Nodo aux = (esArchivoElNodoAInsertar) ? raiz.getAnterior() : raiz.getSiguiente();
        matcher2 = pattern.matcher(aux.getEtiqueta());
        boolean esArchivo = matcher2.matches();
        boolean direccion = esArchivo; //true = getAnterior
        while (aux != raiz)
        {
            matcher2 = pattern.matcher(aux.getSiguiente().getEtiqueta());
            if ((direccion && (nodo.getEtiqueta().compareToIgnoreCase(aux.getEtiqueta()) > 0 || !esArchivo)) || (!direccion && (nodo.getEtiqueta().compareToIgnoreCase(aux.getSiguiente().getEtiqueta()) < 0 || matcher2.matches())))
            {
                nodo.setSiguiente(aux.getSiguiente());
                nodo.setAnterior(aux);
                aux.getSiguiente().setAnterior(nodo);
                aux.setSiguiente(nodo);
                return true;
            }
            aux = (esArchivoElNodoAInsertar) ? aux.getAnterior() : aux.getSiguiente();
            matcher2 = pattern.matcher(aux.getEtiqueta());
            esArchivo = matcher2.matches();
        }
        return false;
    }

    public Nodo eliminar(String etiqueta)
    {
        //Comprueva si la lista esta VACIA.
        if (raiz == null)
        {
            return null;
        }

        Nodo aux = raiz;

        //Comprueva si el NODO a ELIMINAR es el UNICO elemento en la lista.
        if (raiz.getEtiqueta().equals(etiqueta) && raiz.getSiguiente() == raiz && raiz.getAnterior() == raiz)
        {
            raiz = null;
            return aux;
        }

        Matcher matcher = pattern.matcher(etiqueta);
        boolean matches = matcher.matches();

        //Comprueva si el NODO a  ELIMINAR es el PRIMER elemento de la lista.
        if (!matches && raiz.getSiguiente().getEtiqueta().equals(etiqueta))
        {
            aux = raiz.getSiguiente();
            raiz.setSiguiente(aux.getSiguiente());
            raiz.getSiguiente().setAnterior(raiz);
            aux.setSiguiente(null);
            aux.setAnterior(null);
            return aux;
        }

        //Comprueva si el NODO a  ELIMINAR es el ULTIMO elemento de la lista.
        if (matches && raiz.getEtiqueta().equals(etiqueta))
        {
            raiz = aux.getAnterior();
            raiz.setSiguiente(aux.getSiguiente());
            raiz.getSiguiente().setAnterior(raiz);
            aux.setSiguiente(null);
            aux.setAnterior(null);
            return aux;
        }

        //asigna a aux un nodo dependiendo de si la etiqueta pertenece a una carpeta o archivo
        aux = (matches) ? aux.getAnterior() : aux.getSiguiente().getSiguiente();

        matcher = pattern.matcher(aux.getEtiqueta());

        //Busca el nodo a elimiar en la lista excluyendo los extremos
        while (aux != raiz && ((matches && matcher.matches()) || (!matches && !matcher.matches())))
        {
            if ((!matches && aux.getEtiqueta().compareToIgnoreCase(etiqueta) > 0) || (matches && aux.getEtiqueta().compareToIgnoreCase(etiqueta) < 0))
            {
                System.out.print("No encontrado ");
                return null;
            }
            if (aux.getEtiqueta().equals(etiqueta))
            {
                aux.getAnterior().setSiguiente(aux.getSiguiente());
                aux.getSiguiente().setAnterior(aux.getAnterior());
                aux.setSiguiente(null);
                aux.setAnterior(null);
                return aux;
            }
            //aux = aux.getSiguiente();
            aux = (matches) ? aux.getAnterior() : aux.getSiguiente();

            matcher = pattern.matcher(aux.getEtiqueta());
        }
        return null;
    }

        
    public Nodo buscarNodo(String etq)
    {
        if (raiz != null)
        {
            Nodo aux = raiz.getSiguiente();
            do
            {
                if (aux.getEtiqueta().equals(etq))
                {
                    return aux;
                }
                aux = aux.getSiguiente();
            } while (aux != raiz.getSiguiente());
        }
        return null;
    }

    public void desplegarSig()
    {
        if (raiz != null)
        {
            Nodo aux = raiz.getSiguiente();
            do
            {
                System.out.print(aux.getEtiqueta() + "\n");
                aux = aux.getSiguiente();
            } while (aux != raiz.getSiguiente());
            System.out.println("");
        } else
        {
            System.out.println("Lista vacia");
        }
    }
}
