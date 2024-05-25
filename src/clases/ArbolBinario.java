package clases;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alfred
 */
public class ArbolBinario
{

    private NodoArbol r = null;

    /**
     * @return the r
     */
    public NodoArbol getR()
    {
        return r;
    }

    /**
     * @param r the r to set
     */
    public void setR(NodoArbol r)
    {
        this.r = r;
    }

    public NodoArbol inserta(NodoArbol r, NodoArbol n)
    {
        if (r == null)
        {
            return n;
        } else
        {
            if (r.getEtq().compareTo(n.getEtq()) > 0)
            {
                r.setIzq(inserta(r.getIzq(), n));
            } else
            {
                r.setDer(inserta(r.getDer(), n));
            }
            return r;
        }
    }

    public void elimina(NodoArbol raiz, String etq, NodoArbol[] arr)
    {
        if (raiz == null)
        {
            arr[0] = null;
            arr[1] = raiz;
        } else
        {
            if (raiz.getEtq().equals(etq))                          //dato a eliminar encontrado
            {
                arr[0] = raiz;
                if (raiz.getDer() == null && raiz.getIzq() == null)         //no tiene hijos
                {
                    arr[1] = null;
                } else
                {
                    if (!(raiz.getDer() != null && raiz.getIzq() != null))  //tiene un solo hijo
                    {
                        if (raiz.getDer() != null)
                        {
                            arr[1] = raiz.getDer();
                        } else
                        {
                            arr[1] = raiz.getIzq();
                        }
                    } else                                                  //tiene dos hijos
                    {
                        if (raiz.getDer().getIzq() == null)
                        {
                            arr[1] = raiz.getDer();
                            raiz.getDer().setIzq(raiz.getIzq());
                        } else
                        {
                            NodoArbol aux = sucesorEnOrden(raiz.getDer());
                            NodoArbol sucesor = aux.getIzq();
                            aux.setIzq(sucesor.getDer());
                            sucesor.setDer(raiz.getDer());
                            sucesor.setIzq(raiz.getIzq());
                            arr[1] = sucesor;
                        }
                        raiz.setIzq(null);
                        raiz.setDer(null);
                    }
                }
            } else
            {
                if (raiz.getEtq().compareTo(etq) > 0)
                {
                    elimina(raiz.getIzq(), etq, arr);
                    raiz.setIzq(arr[1]);
                } else
                {
                    elimina(raiz.getDer(), etq, arr);
                    raiz.setDer(arr[1]);
                }
                arr[1] = raiz;
            }
        }
    }

    public String enOrden(NodoArbol r)
    {
        String s = "";
        if (r != null)
        {
            s += enOrden(r.getIzq());
            s += r.getEtq();
            s += enOrden(r.getDer());
        }
        return s;
    }

    public String preOrden(NodoArbol r)
    {
        String s = "";
        if (r != null)
        {
            s += r.getEtq();
            s += preOrden(r.getIzq());
            s += preOrden(r.getDer());
        }
        return s;
    }

    public String posOrden(NodoArbol r)
    {
        String s = "";
        if (r != null)
        {
            s += posOrden(r.getIzq());
            s += posOrden(r.getDer());
            s += r.getEtq();
        }
        return s;
    }

    private NodoArbol sucesorEnOrden(NodoArbol raiz)
    {
        if (raiz.getIzq().getIzq() != null)
        {
            return sucesorEnOrden(raiz.getIzq());
        } else
        {
            return raiz;
        }
    }

    public NodoArbol busca(NodoArbol raiz, String et)
    {
        if (raiz != null)
        {
            if (raiz.getEtq().equals(et))
            {
                return raiz;
            } else
            {
                if (raiz.getEtq().compareTo(et) > 0)
                {
                    return busca(raiz.getIzq(), et);
                } else
                {
                    return busca(raiz.getDer(), et);
                }
            }
        } else
        {
            return null;
        }
    }

//    // Método para almacenar los nodos del BST en una lista mediante recorrido in-order
//    private void storeBSTNodes(NodoArbol root, ListaCircularDoblementeLigada lista)
//    {
//        if (root == null)
//        {
//            return;
//        }
//        storeBSTNodes(root.getIzq(), lista);
//        lista.insertar(new Nodo(root.getEtq(), root));
//        storeBSTNodes(root.getDer(), lista);
//    }
//
//    // Método para construir un árbol balanceado a partir de los nodos almacenados
//    private NodoArbol buildTreeUtil(List<NodoArbol> nodes, int start, int end)
//    {
//        if (start > end)
//        {
//            return null;
//        }
//
//        int mid = (start + end) / 2;
//        NodoArbol node = nodes.get(mid);
//
//        node.setIzq(buildTreeUtil(nodes, start, mid - 1));
//        node.setDer(buildTreeUtil(nodes, mid + 1, end));
//
//        return node;
//    }
//
//    public void balancear()
//    {
//        ListaCircularDoblementeLigada listaAux = new ListaCircularDoblementeLigada();
//        storeBSTNodes(r, listaAux);
//        if (!listaAux.esNull())
//        {
//            Nodo aux = listaAux.getRaiz().getSiguiente();
//            int n = 1;
//            while (aux != listaAux.getRaiz())
//            {
//                aux = aux.getSiguiente();
//                n++;
//            }
//            r = buildTreeUtil(listaAux, 0, n - 1);
//        }
//    }
    // Método para almacenar los nodos del BST en una lista mediante recorrido in-order
    private void storeBSTNodes(NodoArbol root, List<NodoArbol> nodes)
    {
        if (root == null)
        {
            return;
        }
        storeBSTNodes(root.getIzq(), nodes);
        nodes.add(root);
        storeBSTNodes(root.getDer(), nodes);
    }

    // Método para construir un árbol balanceado a partir de los nodos almacenados
    private NodoArbol buildTreeUtil(List<NodoArbol> nodes, int start, int end)
    {
        if (start > end)
        {
            return null;
        }

        int mid = (start + end) / 2;
        NodoArbol node = nodes.get(mid);

        node.setIzq(buildTreeUtil(nodes, start, mid - 1));
        node.setDer(buildTreeUtil(nodes, mid + 1, end));

        return node;
    }

    // Método público para equilibrar el árbol
    public void balancear()
    {
        List<NodoArbol> nodes = new ArrayList<>();
        storeBSTNodes(r, nodes);
        int n = nodes.size();
        r = buildTreeUtil(nodes, 0, n - 1);
    }
}
